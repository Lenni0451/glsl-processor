package io.github.ocelot.glslprocessor.api.visitor;

import io.github.ocelot.glslprocessor.api.node.branch.*;
import io.github.ocelot.glslprocessor.api.node.constant.GlslConstantNode;
import io.github.ocelot.glslprocessor.api.node.expression.*;
import io.github.ocelot.glslprocessor.api.node.function.GlslInvokeFunctionNode;
import io.github.ocelot.glslprocessor.api.node.function.GlslPrimitiveConstructorNode;
import io.github.ocelot.glslprocessor.api.node.variable.*;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

/**
 * @author Ocelot
 * @since 1.0.0
 */
@ApiStatus.Experimental
public class GlslNodeVisitor {

    @Nullable
    private final GlslNodeVisitor parent;

    public GlslNodeVisitor() {
        this(null);
    }

    public GlslNodeVisitor(@Nullable final GlslNodeVisitor parent) {
        this.parent = parent;
    }

    /**
     * Visits the specified for loop node.
     *
     * @param node The node to visit
     * @return A visitor for the body or <code>null</code> to skip
     */
    public @Nullable GlslNodeVisitor visitForLoop(final GlslForLoopNode node) {
        return this.parent != null ? this.parent.visitForLoop(node) : null;
    }

    /**
     * Visits the end of the specified for loop node.
     *
     * @param node The node to visit
     */
    public void visitForLoopEnd(final GlslForLoopNode node) {
        if (this.parent != null) {
            this.parent.visitForLoopEnd(node);
        }
    }

    /**
     * Visits the specified while loop node.
     *
     * @param node The node to visit
     * @return A visitor for the body or <code>null</code> to skip
     */
    public @Nullable GlslNodeVisitor visitWhileLoop(final GlslWhileLoopNode node) {
        return this.parent != null ? this.parent.visitWhileLoop(node) : null;
    }

    /**
     * Visits the end of the specified while loop node.
     *
     * @param node The node to visit
     */
    public void visitWhileLoopEnd(final GlslWhileLoopNode node) {
        if (this.parent != null) {
            this.parent.visitWhileLoopEnd(node);
        }
    }

    /**
     * Visits the specified jump node.
     *
     * @param node The node to visit
     */
    public void visitJump(final GlslJumpNode node) {
        if (this.parent != null) {
            this.parent.visitJump(node);
        }
    }

    /**
     * Visits the specified return node.
     *
     * @param node The node to visit
     */
    public void visitReturn(final GlslReturnNode node) {
        if (this.parent != null) {
            this.parent.visitReturn(node);
        }
    }

    /**
     * Visits the specified if statement.
     *
     * @param node The node to visit
     * @return A visitor for the body or <code>null</code> to skip
     */
    public @Nullable GlslIfVisitor visitIf(final GlslIfNode node) {
        return this.parent != null ? this.parent.visitIf(node) : null;
    }

    /**
     * Visits the specified switch statement.
     *
     * @param node The node to visit
     * @return A visitor for the body or <code>null</code> to skip
     */
    public @Nullable GlslSwitchVisitor visitSwitch(final GlslSwitchNode node) {
        return this.parent != null ? this.parent.visitSwitch(node) : null;
    }

    /**
     * Visits the specified bitwise statement.
     *
     * @param node The node to visit
     * @return A visitor for the body or <code>null</code> to skip
     */
    public @Nullable GlslBitwiseVisitor visitBitwise(final GlslBitwiseNode node) {
        return this.parent != null ? this.parent.visitBitwise(node) : null;
    }

    /**
     * Visits the specified assignment statement.
     *
     * @param node The node to visit
     */
    public void visitAssign(final GlslAssignmentNode node) {
        if (this.parent != null) {
            this.parent.visitAssign(node);
        }
    }

    /**
     * Visits the specified operation statement.
     *
     * @param node The node to visit
     */
    public void visitOperation(final GlslOperationNode node) {
        if (this.parent != null) {
            this.parent.visitOperation(node);
        }
    }

    /**
     * Visits the specified compare statement.
     *
     * @param node The node to visit
     */
    public void visitCompare(final GlslCompareNode node) {
        if (this.parent != null) {
            this.parent.visitCompare(node);
        }
    }

    /**
     * Visits the specified condition statement.
     *
     * @param node The node to visit
     */
    public void visitCondition(final GlslConditionalNode node) {
        if (this.parent != null) {
            this.parent.visitCondition(node);
        }
    }

    /**
     * Visits the specified precision statement.
     *
     * @param node The node to visit
     */
    public void visitPrecision(final GlslPrecisionNode node) {
        if (this.parent != null) {
            this.parent.visitPrecision(node);
        }
    }

    /**
     * Visits the specified unary statement.
     *
     * @param node The node to visit
     */
    public void visitUnary(final GlslUnaryNode node) {
        if (this.parent != null) {
            this.parent.visitUnary(node);
        }
    }

    /**
     * Visits the specified function invocation statement.
     *
     * @param node The node to visit
     * @return A visitor for the name and parameters or <code>null</code> to skip
     */
    public @Nullable GlslInvokeVisitor visitFunctionInvocation(final GlslInvokeFunctionNode node) {
        return this.parent != null ? this.parent.visitFunctionInvocation(node) : null;
    }

    /**
     * Visits the specified primitive constructor statement.
     *
     * @param node The node to visit
     */
    public void visitPrimitiveConstructor(final GlslPrimitiveConstructorNode node) {
        if (this.parent != null) {
            this.parent.visitPrimitiveConstructor(node);
        }
    }

    /**
     * Visits the specified constant statement.
     *
     * @param node The node to visit
     */
    public void visitConstant(final GlslConstantNode node) {
        if (this.parent != null) {
            this.parent.visitConstant(node);
        }
    }

    /**
     * Visits the specified variable statement.
     *
     * @param node The node to visit
     */
    public void visitVariableDeclaration(final GlslVariableDeclarationNode node) {
        if (this.parent != null) {
            this.parent.visitVariableDeclaration(node);
        }
    }

    /**
     * Visits the specified array index get statement.
     *
     * @param node The node to visit
     */
    public void visitGetArray(final GlslGetArrayNode node) {
        if (this.parent != null) {
            this.parent.visitGetArray(node);
        }
    }

    /**
     * Visits the specified field get statement.
     *
     * @param node The node to visit
     */
    public void visitGetField(final GlslGetFieldNode node) {
        if (this.parent != null) {
            this.parent.visitGetField(node);
        }
    }

    /**
     * Visits the specified new field statement.
     *
     * @param node The node to visit
     */
    public void visitNewField(final GlslNewFieldNode node) {
        if (this.parent != null) {
            this.parent.visitNewField(node);
        }
    }

    /**
     * Visits the specified struct statement.
     *
     * @param node The node to visit
     */
    public void visitStructDeclaration(final GlslStructDeclarationNode node) {
        if (this.parent != null) {
            this.parent.visitStructDeclaration(node);
        }
    }

    /**
     * Visits the specified variable statement.
     *
     * @param node The node to visit
     */
    public void visitVariable(final GlslVariableNode node) {
        if (this.parent != null) {
            this.parent.visitVariable(node);
        }
    }

}
