package eu.sqooss.metrics.uom;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.osgi.framework.BundleContext;

import eu.sqooss.core.AlitheiaCore;
import eu.sqooss.service.abstractmetric.AbstractMetric;
import eu.sqooss.service.abstractmetric.MetricDecl;
import eu.sqooss.service.abstractmetric.MetricDeclarations;
import eu.sqooss.service.abstractmetric.Result;
import eu.sqooss.service.abstractmetric.SchedulerHints;
import eu.sqooss.service.db.DBService;
import eu.sqooss.service.db.EncapsulationUnit;
import eu.sqooss.service.db.EncapsulationUnitMeasurement;
import eu.sqooss.service.db.ExecutionUnit;
import eu.sqooss.service.db.ExecutionUnitMeasurement;
import eu.sqooss.service.db.Metric;
import eu.sqooss.service.db.ProjectFile;
import eu.sqooss.service.db.ProjectVersion;


import javax.swing.*;

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
        javax.swing.JOptionPane.showMessageDialog(null,"Hello from the Test metric UoM plugin!");
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
