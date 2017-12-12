import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.IOException;
import java.net.URL;

class DataLoader {
    private static final URL DATA_FILE = DataLoader.class.getResource("iris.arff");

    Instances loadDataSet() throws Exception {
        Instances dataSet = read();
        return dataSet;
    }

    private Instances read() throws IOException {
        ArffLoader loader = new ArffLoader();
        loader.setSource(DATA_FILE);
        return loader.getDataSet();
    }
}
