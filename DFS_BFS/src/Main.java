import impl.BFSGraphTraverser;
import impl.CharNode;
import impl.DFSGraphTraverser;
import impl.NodePrinter;
import api.NodeProcessor;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        CharNode A = new CharNode('A');
        CharNode B = new CharNode('B');
        CharNode C = new CharNode('C');
        CharNode D = new CharNode('D');
        CharNode E = new CharNode('E');
        CharNode F = new CharNode('F');
        CharNode G = new CharNode('G');

        A.setNeighbours(Arrays.asList(B, E));
        B.setNeighbours(Arrays.asList(C, D));
        C.setNeighbours(Arrays.asList(F));
        E.setNeighbours(Arrays.asList(G));

        NodeProcessor nodeProcessor = new NodePrinter();

        BFSGraphTraverser bfsGraphTraverser = new BFSGraphTraverser(nodeProcessor);
        System.out.println("---------BFS Traversing----------");
        bfsGraphTraverser.traverse(A);

        DFSGraphTraverser dfsGraphTraverser = new DFSGraphTraverser(nodeProcessor);
        System.out.println("---------DFS Traversing----------");
        dfsGraphTraverser.traverse(A);
    }
}
