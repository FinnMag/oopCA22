package C20492576;

import ie.assignment.OOP;
import processing.core.PApplet;

public class FractalTree extends PApplet {

    OOP tree;
    DynamicColour dc;
    FractalTree start;

    int treeCount;
    float amplitude;
    int noBranches;
    float angle = 0;

    FractalTree[] branches;

    float rotateAngle;

    public FractalTree(OOP tree, float amplitude, float angle, int noBranches, Colours cl, int treeCount) {

        this.tree = tree;
        this.amplitude = amplitude;
        this.angle = angle;
        this.treeCount = treeCount;
        this.noBranches = noBranches;
        branch(noBranches, cl);
        dc = new DynamicColour(tree, cl);

    }

    void display() {

        tree.rotate(this.angle);

        tree.line(0, 0, 0, amplitude);

        tree.pushMatrix();

        if (branches != null) {
            tree.translate(0, amplitude);
            tree.pushMatrix();
            branches[0].display();
            tree.popMatrix();
            branches[1].display();
        }

        tree.popMatrix();
    }

    void branch(int noBranches, Colours cl) {

        if (noBranches > 0) {

            float angle = OOP.map(tree.getSmoothedAmplitude(), 0, 1, 3.14f / 10f, 3.14f / 2f);

            branches = new FractalTree[2];


            branches[0] = new FractalTree(tree, amplitude / tree.scrollVal, angle, noBranches - 2, cl, treeCount);
            branches[1] = new FractalTree(tree, amplitude / tree.scrollVal, -angle, noBranches - 2, cl, treeCount);

        }
    }

    public void render() {

        tree.rotationCycle++;

        if (tree.changeVisual == true)
        {
            if (tree.rotateDirection == 0)
                rotateAngle = map(tree.rotationCycle % 360, 0, 360, 0, OOP.PI * 4);

            if (tree.rotateDirection == 1)
                rotateAngle = map(-tree.rotationCycle % 360, 0, 360, 0, OOP.PI * 4);

            tree.rotate(rotateAngle);

        }

        // mouse control
        if (tree.changeVisual == false) {
            tree.getMouseAngle();
            tree.rotate(tree.rotationAngle);
        }

        for (int i = 0; i < treeCount; i++) {

            tree.rotate(OOP.PI / (treeCount / 2));
            tree.pushMatrix();

            tree.translate(tree.width / 2, tree.height / 2);
            
            tree.popMatrix();

            dc.changeColour(0.1f);

            // tree.stroke((tree.rotationCycle / 2) % 255, (tree.rotationCycle / 3) % 255,
            // (tree.rotationCycle / 4) % 255);

            display();

        }

    }
}
