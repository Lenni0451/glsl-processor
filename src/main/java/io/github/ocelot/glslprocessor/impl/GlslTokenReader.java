package io.github.ocelot.glslprocessor.impl;

import io.github.ocelot.glslprocessor.api.GlslSyntaxException;
import io.github.ocelot.glslprocessor.api.node.GlslNode;
import lombok.ToString;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * @author Ocelot
 */
@ToString
@ApiStatus.Internal
public class GlslTokenReader {

    private static final Pattern MARKER_PATTERN = Pattern.compile(" *#\\s*(.+)");

    private final Map<String, Integer> markers;
    private final Map<String, GlslNode> markedNodes;
    private final GlslLexer.Token[] tokens;
    private final String tokenString;
    private int cursor;
    private final List<GlslSyntaxException> errors;
    private final List<GlslSyntaxException> errorsView;

    public GlslTokenReader(final String source) throws GlslSyntaxException {
        this.markers = new HashMap<>();
        this.markedNodes = new HashMap<>();
        this.tokens = GlslLexer.createTokens(source, (comment, index) -> {
            String value = comment.value().substring(2);
            if (comment.type() == GlslLexer.TokenType.MULTI_COMMENT) {
                value = value.substring(0, value.length() - 2);
            }

            Matcher matcher = MARKER_PATTERN.matcher(value);
            if (matcher.find()) {
                this.markers.put(matcher.group(1).toLowerCase(Locale.ROOT), index);
            }
        });
        this.tokenString = calculateString(source.length() + this.tokens.length, this.tokens);
        this.cursor = 0;
        this.errors = new ArrayList<>();
        this.errorsView = Collections.unmodifiableList(this.errors);
    }

    public GlslTokenReader(final GlslLexer.Token[] tokens) {
        this.markers = Collections.emptyMap();
        this.markedNodes = new HashMap<>();
        this.tokens = tokens;
        this.tokenString = calculateString(tokens.length * 8, tokens);
        this.cursor = 0;
        this.errors = new ArrayList<>();
        this.errorsView = Collections.unmodifiableList(this.errors);
    }

    private static String calculateString(final int length, final GlslLexer.Token[] tokens) {
        StringBuilder builder = new StringBuilder(length);
        for (GlslLexer.Token token : tokens) {
            builder.append(token.value());
        }
        return builder.toString().trim();
    }

    public int getCursorOffset(final int cursor) {
        int offset = 0;
        for (int i = 0; i <= Math.min(cursor, this.tokens.length - 1); i++) {
            offset += this.tokens[i].value().length();
        }
        return offset;
    }

    public boolean canRead(final int length) {
        return this.cursor + length <= this.tokens.length;
    }

    public boolean canRead() {
        return this.canRead(1);
    }

    public GlslLexer.Token peek() {
        return this.peek(0);
    }

    public GlslLexer.Token peek(final int amount) {
        return this.cursor + amount < this.tokens.length ? this.tokens[this.cursor + amount] : null;
    }

    public GlslLexer.TokenType peekType(final int amount) {
        return this.cursor + amount < this.tokens.length ? this.tokens[this.cursor + amount].type() : null;
    }

    public boolean canConsume(final GlslLexer.TokenType token) {
        return this.canRead() && this.peek().type() == token;
    }

    public GlslLexer.Token consume(final GlslLexer.TokenType token) throws GlslSyntaxException {
        if (!this.canRead() || this.peek().type() != token) {
            throw this.error("Expected " + token);
        }
        this.cursor++;
        return this.peek(-1);
    }

    public boolean tryConsume(final GlslLexer.TokenType... tokens) {
        if (!this.canRead(tokens.length)) {
            return false;
        }

        for (int i = 0; i < tokens.length; i++) {
            if (this.peek(i).type() != tokens[i]) {
                return false;
            }
        }
        this.cursor += tokens.length;
        return true;
    }

    public GlslSyntaxException error(final String error) {
        return new GlslSyntaxException(error, this.tokenString, this.getCursorOffset(this.cursor));
    }

    @Contract("->fail")
    public void throwError() throws GlslSyntaxException {
        if (this.errors.isEmpty()) {
            throw new GlslSyntaxException("Failed", this.tokenString, this.cursor);
        }

        int cursor = this.cursor;
        int[] cursors = this.errors.stream().mapToInt(GlslSyntaxException::getCursor).toArray();
        int[] cursorOffsets = new int[cursors.length];

        int offset = 0;
        for (int i = 0; i <= Math.min(Math.max(cursor, IntStream.of(cursors).max().orElse(0)), this.tokens.length - 1); i++) {
            offset += this.tokens[i].value().length();
            if (i == this.cursor) {
                cursor = offset;
            }
            for (int j = 0; j < cursors.length; j++) {
                if (i == cursors[j]) {
                    cursorOffsets[j] = offset;
                }
            }
        }

        GlslSyntaxException exception = new GlslSyntaxException("Failed", this.tokenString, this.getCursorOffset(this.cursor));
        for (int i = 0; i < this.errors.size(); i++) {
            GlslSyntaxException error = this.errors.get(i);
            error.setCursor(cursorOffsets[i]);
            exception.addSuppressed(error);
        }
        throw exception;
    }

    public void skip() {
        this.cursor++;
    }

    public void skip(final int amount) {
        this.cursor += amount;
    }

    public void markError(final String message) {
        for (GlslSyntaxException error : this.errors) {
            if (error.getCursor() == this.cursor && error.getRawMessage().equals(message)) {
                return;
            }
        }
        this.errors.add(new GlslSyntaxException(message, this.tokenString, this.cursor));
    }

    public void markNode(final int cursor, final GlslNode node) {
        for (Map.Entry<String, Integer> entry : this.markers.entrySet()) {
            if (entry.getValue() == cursor) {
                this.markedNodes.put(entry.getKey(), node);
            }
        }
    }

    /**
     * @return All errors marked from reading tokens
     */
    public List<GlslSyntaxException> getErrors() {
        return this.errorsView;
    }

    public int getCursor() {
        return this.cursor;
    }

    public Map<String, GlslNode> getMarkedNodes() {
        return this.markedNodes;
    }

    public void setCursor(final int cursor) {
        this.cursor = cursor;
    }


    public record Error(int position, String message) {
    }

}
