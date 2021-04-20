package graphs.usage;

import graphs.secondversion.Prim;
import graphs.secondversion.Graph;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;

public class PrimUsageV2 {

	private static final Charset ENCODING = StandardCharsets.UTF_8;

	/**
	 * 
	 * @param args the command line arguments
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws Exception {

		if (args.length < 1)
			throw new Exception("PrimUsageV2 needs an argument");

		Graph g = new Graph(false);
		loadAllEntries(args[0], g);
		System.out.println("Graph created");

		Graph mst = Prim.mstPrim(g, "A", new MinComparator());
		System.out.println("Vertex count: " + mst.vertexCount());
		System.out.println("Edge count: " + mst.edgeCount());
		DecimalFormat formatter = new DecimalFormat("#0.000");
		System.out.println("Total weight: " + formatter.format(mst.weight() / 1000) + " km");

	}

	/**
	 * 
	 * @param filepath
	 * @param g
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void loadAllEntries(String filepath, Graph g) throws IOException {
		System.out.println("Loading entries from " + filepath + " file...");
		Path inputFilePath = Paths.get(filepath);
		try (BufferedReader fileInputReader = Files.newBufferedReader(inputFilePath, ENCODING)) {
			String line = null;
			while ((line = fileInputReader.readLine()) != null) {
				String[] content = line.split(",");
				g.addEdgeForced(content[0], content[1], Double.parseDouble(content[2]));
			}
		}
		System.out.println("All entries loaded");
	}

}
