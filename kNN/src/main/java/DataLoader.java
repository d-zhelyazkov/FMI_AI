import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

import java.io.IOException;
import java.net.URL;

class DataLoader {
    private static final URL DATA_FILE = DataLoader.class.getResource("iris.arff");

    Instances loadDataSet() throws Exception {
        Instances dataSet = read();
        dataSet = normalize(dataSet);
        return dataSet;
    }

    private Instances read() throws IOException {
        ArffLoader loader = new ArffLoader();
        loader.setSource(DATA_FILE);
        return loader.getDataSet();
    }

    private Instances normalize(Instances dataSet) throws Exception {
        Normalize normalize = new Normalize();
        normalize.setInputFormat(dataSet);
        return Filter.useFilter(dataSet, normalize);
    }
}
