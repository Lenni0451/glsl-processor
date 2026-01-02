package io.github.ocelot.glslprocessor.api.node;

import io.github.ocelot.glslprocessor.api.grammar.GlslSpecifiedType;
import io.github.ocelot.glslprocessor.api.node.constant.GlslBoolConstantNode;
import io.github.ocelot.glslprocessor.api.node.constant.GlslDoubleConstantNode;
import io.github.ocelot.glslprocessor.api.node.constant.GlslFloatConstantNode;
import io.github.ocelot.glslprocessor.api.node.constant.GlslIntConstantNode;
import io.github.ocelot.glslprocessor.api.visitor.GlslNodeStringWriter;
import io.github.ocelot.glslprocessor.api.visitor.GlslNodeVisitor;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * Represents a single operation in a {@link GlslTree}.
 *
 * @author Ocelot
 * @since 1.0.0
 */
@ApiStatus.NonExtendable
public interface GlslNode {

    static GlslIntConstantNode intConstant(final int value) {
        return new GlslIntConstantNode(GlslIntConstantNode.Format.DECIMAL, true, value);
    }

    static GlslIntConstantNode unsignedIntConstant(final int value) {
        return new GlslIntConstantNode(GlslIntConstantNode.Format.DECIMAL, false, value);
    }

    static GlslFloatConstantNode floatConstant(final float value) {
        return new GlslFloatConstantNode(value);
    }

    static GlslDoubleConstantNode doubleConstant(final double value) {
        return new GlslDoubleConstantNode(value);
    }

    static GlslBoolConstantNode booleanConstant(final boolean value) {
        return new GlslBoolConstantNode(value);
    }

    static GlslNode compound(final Collection<GlslNode> nodes) {
        if (nodes.isEmpty()) {
            return GlslEmptyNode.INSTANCE;
        }
        if (nodes.size() == 1) {
            return nodes.iterator().next();
        }
        List<GlslNode> list = new ArrayList<>();
        for (GlslNode node : nodes) {
            if (!(node instanceof GlslCompoundNode compoundNode)) {
                list.clear();
                list.addAll(nodes);
                break;
            }
            list.addAll(compoundNode.getChildren());
        }
        return new GlslCompoundNode(list);
    }

    static GlslNode compound(final GlslNode... nodes) {
        if (nodes.length == 0) {
            return GlslEmptyNode.INSTANCE;
        }
        if (nodes.length == 1) {
            return nodes[0];
        }
        return new GlslCompoundNode(new ArrayList<>(Arrays.asList(nodes)));
    }


    /**
     * @return The type of node this class represents
     */
    GlslNodeType getNodeType();

    /**
     * Visits this node.
     *
     * @param visitor The visitor visiting this node
     */
    void visit(GlslNodeVisitor visitor);

    Stream<GlslNode> stream();

    default @Nullable GlslSpecifiedType getSpecifiedType() {
        return null;
    }

    /**
     * @return This node represented as a string
     * @see GlslNodeStringWriter
     */
    default String toSourceString() {
        GlslNodeStringWriter visitor = new GlslNodeStringWriter(true);
        this.visit(visitor);
        visitor.trimSemicolon();
        return visitor.toString();
    }

}
