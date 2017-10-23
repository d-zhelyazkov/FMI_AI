package impl;

import api.Node;
import api.NodeProcessor;

/**
 * Created by dzhel on 7.10.2017 г..
 */
public class NodePrinter implements NodeProcessor {
    @Override
    public void process(Node node) {
        System.out.println(node);
    }
}
