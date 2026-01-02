package io.github.ocelot.glslprocessor.api.node.variable;

import io.github.ocelot.glslprocessor.api.grammar.GlslSpecifiedType;
import io.github.ocelot.glslprocessor.api.grammar.GlslStructSpecifier;
import io.github.ocelot.glslprocessor.api.node.GlslNode;
import io.github.ocelot.glslprocessor.api.node.GlslNodeType;
import io.github.ocelot.glslprocessor.api.node.GlslRootNode;
import io.github.ocelot.glslprocessor.api.visitor.GlslNodeVisitor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

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
public final class GlslStructDeclarationNode implements GlslRootNode {

    private GlslSpecifiedType type;

    public GlslStructDeclarationNode(final GlslSpecifiedType specifiedType) {
        if (!specifiedType.getSpecifier().isStruct()) {
            throw new IllegalArgumentException("specified type must be struct or array of structs");
        }
        this.type = specifiedType;
    }

    public GlslStructDeclarationNode setType(final GlslSpecifiedType specifiedType) {
        if (!specifiedType.getSpecifier().isStruct()) {
            throw new IllegalArgumentException("specified type must be struct or array of structs");
        }
        this.type = specifiedType;
        return this;
    }

    public GlslStructSpecifier getStructSpecifier() {
        return this.type.getSpecifier().asStructSpecifier();
    }

    @Override
    public String getName() {
        return this.type.getSpecifier().getName();
    }

    @Override
    public GlslStructDeclarationNode setName(final String name) {
        this.getStructSpecifier().setName(name);
        return this;
    }

    @Override
    public GlslNodeType getNodeType() {
        return GlslNodeType.STRUCT_DECLARATION;
    }

    @Override
    public void visit(GlslNodeVisitor visitor) {
        visitor.visitStructDeclaration(this);
    }

    @Override
    public Stream<GlslNode> stream() {
        return Stream.of(this);
    }

}
