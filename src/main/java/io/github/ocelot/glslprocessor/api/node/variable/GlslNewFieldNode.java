package io.github.ocelot.glslprocessor.api.node.variable;

import io.github.ocelot.glslprocessor.api.grammar.GlslSpecifiedType;
import io.github.ocelot.glslprocessor.api.grammar.GlslType;
import io.github.ocelot.glslprocessor.api.node.GlslNode;
import io.github.ocelot.glslprocessor.api.node.GlslNodeType;
import io.github.ocelot.glslprocessor.api.node.GlslRootNode;
import io.github.ocelot.glslprocessor.api.visitor.GlslNodeVisitor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

/**
 * @author Ocelot
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public final class GlslNewFieldNode implements GlslRootNode {

    private GlslSpecifiedType type;
    private String name;
    private GlslNode initializer;

    public GlslNewFieldNode(final GlslType type, @Nullable final String name, @Nullable final GlslNode initializer) {
        this(type.asSpecifiedType(), name, initializer);
    }

    public GlslNewFieldNode(final GlslSpecifiedType type, @Nullable final String name, @Nullable final GlslNode initializer) {
        this.type = type;
        this.name = name;
        this.initializer = initializer;
    }

    @Override
    public @Nullable String getName() {
        return this.name;
    }

    public GlslNewFieldNode setType(final GlslType type) {
        this.type = type.asSpecifiedType();
        return this;
    }

    @Override
    public GlslNodeType getNodeType() {
        return GlslNodeType.NEW_FIELD;
    }

    @Override
    public void visit(GlslNodeVisitor visitor) {
        visitor.visitNewField(this);
    }

    @Override
    public Stream<GlslNode> stream() {
        return this.initializer != null ? Stream.concat(Stream.of(this), this.initializer.stream()) : Stream.of(this);
    }

    @Override
    public @NotNull GlslSpecifiedType getSpecifiedType() {
        return this.type;
    }

}
