import java.util.ArrayList;
class Graph {
   private final int MAX_VERTS = 8;
   private Vertex vertexList[];
   private int adjMat[][];
   private int nVerts;
   private StackX theStack;
   private Queue theQueue;

   // ------------------------------------------------------------
   public Graph() {
      vertexList = new Vertex[MAX_VERTS];

      adjMat = new int[MAX_VERTS][MAX_VERTS];
      nVerts = 0;
      for (int y = 0; y < MAX_VERTS; y++)
         for (int x = 0; x < MAX_VERTS; x++)
            adjMat[x][y] = 0;

      theStack = new StackX();
      theQueue = new Queue();

   }

   // ------------------------------------------------------------
   public void addVertex(char lab) {
      vertexList[nVerts++] = new Vertex(lab);
   }

   // ------------------------------------------------------------
   public void addEdge(int start, int end) {
      adjMat[start][end] = 1;
      adjMat[end][start] = 1;
   }

   // ------------------------------------------------------------
   public void displayVertex(int v) {
      System.out.print(vertexList[v].label);
   }

   // -------------------------------------------------------------
   public void bfs() {
      vertexList[0].wasVisited = true;
      displayVertex(0);
      theQueue.insert(0);
      int v2;

      while (!theQueue.isEmpty()) {
         int v1 = theQueue.remove();

         while ((v2 = getAdjUnvisitedVertex(v1)) != -1) {
            vertexList[v2].wasVisited = true;
            displayVertex(v2);
            theQueue.insert(v2);
         }
      }

      for (int j = 0; j < nVerts; j++)
         vertexList[j].wasVisited = false;
   }

   // ------------------------------------------------------------
   public void dfs() {
      vertexList[0].wasVisited = true;
      displayVertex(0);
      theStack.push(0);

      while (!theStack.isEmpty()) {
         int v = getAdjUnvisitedVertex(theStack.peek());
         if (v == -1)
            theStack.pop();
         else {
            vertexList[v].wasVisited = true;
            displayVertex(v);
            theStack.push(v);
         }
      }

      for (int j = 0; j < nVerts; j++)
         vertexList[j].wasVisited = false;
   }

   // -------------------------------------------------------------
   public void mst() {
      vertexList[0].wasVisited = true;
      theStack.push(0);

      while (!theStack.isEmpty()) {
         int currentVertex = theStack.peek();
         int v = getAdjUnvisitedVertex(currentVertex);
         if (v == -1)
            theStack.pop();
         else {
            vertexList[v].wasVisited = true;
            theStack.push(v);

            displayVertex(currentVertex);
            displayVertex(v);
            System.out.print(" ");
         }
      }

      for (int j = 0; j < nVerts; j++)
         vertexList[j].wasVisited = false;
   }

   // -------------------------------------------------------------
   public int getAdjUnvisitedVertex(int v) {
      for (int j = 0; j < nVerts; j++)
         if (adjMat[v][j] == 1 && vertexList[j].wasVisited == false)
            return j;
      return -1;
   }

   // -------------------------------------------------------------
   public void efs(char initialState, char finalState)  {

      ArrayList<Path> path = new ArrayList<Path>();

      int indexInitialState = getIndexByChar(initialState);
      int indexFinalState = getIndexByChar(finalState);

      vertexList[indexInitialState].wasVisited = true;
      theQueue.insert(indexInitialState);
      int v2;

      while (!theQueue.isEmpty()) {
         int v1 = theQueue.remove();

         while ((v2 = getAdjUnvisitedVertex(v1)) != -1) {
            vertexList[v2].wasVisited = true;
            path.add(new Path(getCharByIndex(v1),getCharByIndex(v2)));
            theQueue.insert(v2);
         }
      }

      ArrayList<Integer> resultPath = new ArrayList<Integer>();
      reversePath(path,initialState,finalState, resultPath);
      
      while(!resultPath.isEmpty()) {
         System.out.print(getCharByIndex(resultPath.remove(resultPath.size()-1)));
         if(resultPath.size() > 0)
            System.out.print(" -> ");
      }
      for (int j = 0; j < nVerts; j++)
         vertexList[j].wasVisited = false;
   }


   // -------------------------------------------------------------
   public int getIndexByChar(char c) {
      for (int i = 0; i < nVerts; i++) {
         if (vertexList[i].label == c) 
            return i;   
         
      }
      return -1;
   }

   // -------------------------------------------------------------
   public char getCharByIndex(int i) {
      return vertexList[i].label;
   }

   public void reversePath(ArrayList<Path> path,char initialState, char finalState, ArrayList<Integer> result) {

      for(Path p: path) {
         if(finalState == initialState){
            result.add(getIndexByChar(initialState));
            return;
         }
         if(p.to == finalState) {
            result.add(getIndexByChar(p.to));
            reversePath(path,initialState,p.from,result);
         }
      }   
   }
   // -------------------------------------------------------------
}