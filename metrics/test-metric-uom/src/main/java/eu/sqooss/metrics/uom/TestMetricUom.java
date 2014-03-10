package eu.sqooss.metrics.uom;

import eu.sqooss.service.abstractmetric.*;
import eu.sqooss.service.db.*;
import org.osgi.framework.BundleContext;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

@MetricDeclarations(metrics = {
  @MetricDecl(mnemonic = "TEST_UOM", activators = {ExecutionUnit.class, ProjectVersion.class}, descr = "Test UoM metric"),
})
@SchedulerHints(activationOrder = {ProjectVersion.class, EncapsulationUnit.class})
public class TestMetricUom extends AbstractMetric {

    private List<ProjectFile> changedFiles;
    private ProjectVersion pv;
    //Class -> Base 
    private ConcurrentMap<String, String> reducer;
    private DBService db;

    public TestMetricUom(BundleContext bc) {
        super(bc);

        testMath();
    }

    public void testMath(){
        DescriptiveStatistics stats = new DescriptiveStatistics();

        int[] inputArray = new int[]{15,60,950,45,78,41,23,350};

        for( int i = 0; i < inputArray.length; i++) {
            stats.addValue(inputArray[i]);
        }

// Compute some statistics
        double mean = stats.getMean();
        double std = stats.getStandardDeviation();
        double median = stats.getPercentile(50);
        JOptionPane.showMessageDialog(null,"Mean = "+mean+"\n std = "+std+"\n median = "+median);
    }

    public List<Result> getResult(ProjectFile a, Metric m) {
        return null;
    }

    public List<Result> getResult(ProjectVersion a, Metric m) {
        return null;
    }

    public List<Result> getResult(ExecutionUnit a, Metric m) {
        return getResult(a, ExecutionUnitMeasurement.class,
                m, Result.ResultType.INTEGER);
    }

    public List<Result> getResult(EncapsulationUnit a, Metric m) {
        return getResult(a, EncapsulationUnitMeasurement.class,
                m, Result.ResultType.INTEGER);
    }

    public void run(ProjectFile pf) throws Exception {
        javax.swing.JOptionPane.showMessageDialog(null,"Hello from the Test metric UoM plugin! Method: run(ProjectFile pf) ");
    }

    public void run(EncapsulationUnit wu) throws Exception {
        javax.swing.JOptionPane.showMessageDialog(null,"Hello from the Test metric UoM plugin! Method: run(EncapsulationUnit wu) ");
    }

    public void run(ExecutionUnit eu) throws Exception {
        javax.swing.JOptionPane.showMessageDialog(null,"Hello from the Test metric UoM plugin! Method: run(ExecutionUnit eu) ");
    }

    public void run(ProjectVersion pv) throws Exception {
        javax.swing.JOptionPane.showMessageDialog(null,"Hello from the Test metric UoM plugin! Method: run(ProjectVersion pv)");
    }

    protected void parseFile(ProjectFile pf) throws Exception {
    }

    protected void warn(String... strings) {
        log.warn(getMsg(strings));
    }

    protected void err(String... strings) {
        log.error(getMsg(strings));
    }

    protected void info(String... strings) {
        log.info(getMsg(strings));
    }

    protected void debug(String... strings) {
        if (log != null)
            log.debug(getMsg(strings));
        else
            System.err.println(getMsg(strings));
    }

    private String getMsg(String... strings) {
        StringBuffer b = new StringBuffer();
        b.append("TestMetricUom:").append(pv).append(":");
        for (String str : strings) {
            b.append(str);
        }
        return b.toString();
    }

    @Override
    public String toString() {
        return "TestMetricUom: Version:" + pv;
    }
}
