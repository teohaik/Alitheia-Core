/*
 * Copyright 2008 - Organization for Free and Open Source Software,  
 *                  Athens, Greece.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials provided
 *       with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

/*
** That copyright notice makes sense for code residing in the 
** main SQO-OSS repository. For the TestMetric plug-in only, the Copyright
** notice may be removed and replaced by a statement of your own
** with (compatible) license terms as you see fit; the TestMetric
** plug-in itself is insufficiently a creative work to be protected
** by Copyright.
*/

/* This is the package for this particular plug-in. Third-party
** applications will want a different package name, but it is
** *ESSENTIAL* that the package name contain the string '.metrics.'
** because this is how Alitheia Core discovers the metric plug-ins. 
*/
package gr.uom.alitheia.metrics;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import eu.sqooss.core.AlitheiaCore;
import eu.sqooss.service.db.*;
import org.osgi.framework.BundleContext;

/* These are imports of standard Alitheia core services and types.
** You are going to need these anyway; some others that you might
** need are the FDS and other Metric interfaces, as well as more
** DAO types from the database service.
*/
import eu.sqooss.service.abstractmetric.AbstractMetric;
import eu.sqooss.service.abstractmetric.MetricDecl;
import eu.sqooss.service.abstractmetric.MetricDeclarations;
import eu.sqooss.service.abstractmetric.Result;

/**
 * The TestMetric class is the bit that actually implements the metrics in this
 * plug-in. It must extend AbstractMetric (so that it can be called by the
 * various metrics drivers).
 *  
 */ 
@MetricDeclarations(metrics= {
	@MetricDecl(mnemonic="DUMMYM", activators={ProjectVersion.class},
			descr="TestMetric UoM Dummy Metric", dependencies={"Wc.loc"})
})
public class TestMetric extends AbstractMetric {

    /**
     * The current project version we are examining
     */
    private ProjectVersion pv;

    /**
     * Alitheia Core's DB service
     */
    private DBService db;

    /**
     * The pattern for .java files
     */
    static private Pattern javaFilePattern = Pattern.compile("([^\\s]+(\\.(?i)(java))$)");

    /**
     * To query for stored measurement of metric Wc.loc
     */
    private static Metric loc = null;

    public TestMetric(BundleContext bc) {
        super(bc);        
    }

    public List<Result> getResult(ProjectFile a, Metric m) {
        // Return a list of ResultEntries by querying the DB for the 
        // measurements implement by the supported metric and calculated 
        // for the specific project file.
        return null;
    }

    /**
     * A dummy implementation of run method to calculate a metric.
     * Given the project version (revision) it will calculate the total
     * number of java files, and the total lines of code. The results will
     * be outputted to std.
     * WARNING: this method requires the Wc.loc metric to be calculated first.
     * Alitheia Core framework will take care of metric dependency and will calculate
     * Wc.loc metric if it is not calculated for the given revision.
     *
     * @param projectVersion The version to calculate the metric against
     */
    public void run(ProjectVersion projectVersion) {

        if(loc == null) {
            loc = Metric.getMetricByMnemonic("Wc.loc");
        }

        // get the db service
        db = AlitheiaCore.getInstance().getDBService();
        // attach the project version to db service
        projectVersion = db.attachObjectToDBSession(projectVersion);
        this.pv = projectVersion;

        int noJFiles = 0;
        int noLines = 0;
        System.out.println("Discovering files for project revision: " + projectVersion.getRevisionId());

        for(ProjectFile pFile : this.pv.getFiles(javaFilePattern)) {
            pFile = db.attachObjectToDBSession(pFile);
            // Get ProjectFile's measurements (one of them must be Wc.loc)
            // get the number of lines for current file and add them to
            // noLines.
            noLines += getMeasurement(pFile, loc);
            noJFiles++;
        }

        System.out.println("Number of Java files: " + noJFiles);
        System.out.println("Total lines of Java files: " + noLines);
    }

    /**
     * Given a project file, find the measurement that is stored in DB for the
     * specified metric.
     * @param pf
     * @param m
     * @return
     */
    private int getMeasurement(ProjectFile pf, Metric m) {

        for(ProjectFileMeasurement pm : pf.getMeasurements()) {
            if(pm.getMetric().equals(m)) {
                return Integer.parseInt(pm.getResult());
            }
        }

        return 0;
    }
}

// vi: ai nosi sw=4 ts=4 expandtab
