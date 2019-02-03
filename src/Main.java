import java.io.*;
import java.util.HashMap;

public class Main {
    public static void main(String[] a) {
        //exon list
        HashMap<String, Double> eMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(a[1]))) {
            for (String line; (line = br.readLine()) != null; ) {
                String[] values = line.split("\t");
                eMap.put(values[1], Double.parseDouble(values[0]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //gene-transcript-exon mapping
        StringBuilder sb = new StringBuilder();
        double f = -1;
        try (BufferedReader br = new BufferedReader(new FileReader(a[0]))) {
            for (String line; (line = br.readLine()) != null; ) {
                String[] values = line.split("\t");
                if (values[5].equals("1")) {
                    if (f != -1) {
                        sb.append(f);
                        sb.append("\n");
                    }
                    f = 1;
                    sb.append(values[2]);
                    sb.append("\t");
                    sb.append(values[3]);
                    sb.append("\t");
                }
                f *= eMap.get(values[6]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sb.append(f);

        //output
        try (FileWriter fw = new FileWriter(a[2], false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.print(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
