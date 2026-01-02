package io.github.ocelot.glslprocessor.api.node.variable;

import io.github.ocelot.glslprocessor.api.grammar.GlslTypeQualifier;
import io.github.ocelot.glslprocessor.api.node.GlslNode;
import io.github.ocelot.glslprocessor.api.node.GlslNodeType;
import io.github.ocelot.glslprocessor.api.node.GlslRootNode;
import io.github.ocelot.glslprocessor.api.visitor.GlslNodeVisitor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
public final class GlslVariableDeclarationNode implements GlslRootNode {

    private final List<GlslTypeQualifier> typeQualifiers;
    private final List<String> names;

    public GlslVariableDeclarationNode(final Collection<GlslTypeQualifier> typeQualifiers, final Collection<String> names) {
        this.typeQualifiers = new ArrayList<>(typeQualifiers);
        this.names = new ArrayList<>(names);
    }

    @Override
    public @Nullable String getName() {
        throw new UnsupportedOperationException("Use getNames() instead");
    }

    @Override
    public GlslRootNode setName(@Nullable String name) {
        throw new UnsupportedOperationException("Use setNames() instead");
    }

    @Override
    public GlslNodeType getNodeType() {
        return GlslNodeType.VARIABLE_DECLARATION;
    }

    @Override
    public void visit(GlslNodeVisitor visitor) {
        visitor.visitVariableDeclaration(this);
    }

    @Override
    public Stream<GlslNode> stream() {
        return Stream.of(this);
    }

}
