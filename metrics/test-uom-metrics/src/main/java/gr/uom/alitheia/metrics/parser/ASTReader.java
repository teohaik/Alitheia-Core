package gr.uom.alitheia.metrics.parser;
  /*
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jdt.core.dom.*;

import javax.swing.*;

    */
public class ASTReader {
                      /*
    ArrayList<String> javaFileListPaths;
    ASTParser parser;
    String rootDirCanonicalPath;

    public ASTReader() throws IOException{
        javaFileListPaths = new ArrayList<String>();

        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(null);
        rootDirCanonicalPath =  fc.getSelectedFile().getCanonicalPath();
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            walk(rootDirCanonicalPath);
        }
        parser = ASTParser.newParser(AST.JLS3);
        parser.setEnvironment(null, new String[] { rootDirCanonicalPath }, null, true);
        parser.setResolveBindings(true);
        parser.setStatementsRecovery(true);
        parser.setBindingsRecovery(true);

        parseAllFilesAndCreateASTs();
    }

    public ASTReader(String rootDirCanonicalPath) throws IOException{
        walk(rootDirCanonicalPath);

        parser = ASTParser.newParser(AST.JLS3);
        parser.setEnvironment(null, new String[] { rootDirCanonicalPath }, null, true);
        parser.setResolveBindings(true);
        parser.setStatementsRecovery(true);
        parser.setBindingsRecovery(true);

        parseAllFilesAndCreateASTs();
    }

    private void walk(String path ) throws IOException {
        File root = new File( path );
        File[] list = root.listFiles();
        if (list == null) return;
        for ( File f : list ) {
            if ( f.isDirectory() ) {
                walk( f.getAbsolutePath() );
                //   System.out.println( "Dir:" + f.getAbsoluteFile() );
            }
            else {
                if(f.getAbsolutePath().endsWith(".java")){
                    javaFileListPaths.add(f.getAbsolutePath());
                }
            }
        }
    }

    private void parseAllFilesAndCreateASTs() throws IOException {
        String[] stringDemo = new String[1];
        String[] canonicalPaths = javaFileListPaths.toArray(stringDemo);
        String[] bindingKeys = new String[javaFileListPaths.size()];
        int i=0;
        for(String javaFilePath : javaFileListPaths){
            bindingKeys[i] = createBindingKeyFromClassFile(javaFilePath);
            i++;
        }
        MyFileASTRequestor myFileASTRequestor = new MyFileASTRequestor();

        parser.createASTs(canonicalPaths, null, bindingKeys, myFileASTRequestor, null);

    }

    public String createBindingKeyFromClassFile(String filePath) throws IOException {
        String classString = readFileToString(filePath);
        int packageDeclarationStart = classString.indexOf("package");
        int packageDeclarationEnd = classString.indexOf(";", packageDeclarationStart);
        String packageDeclarationLine = classString.substring(packageDeclarationStart,packageDeclarationEnd);
        String packageName = packageDeclarationLine.substring(packageDeclarationLine.lastIndexOf("package")+7, packageDeclarationLine.length());
        String className = filePath.substring(filePath.lastIndexOf(File.separator), filePath.indexOf(".java"));
        String fullyQualifiedClassName = packageName+"."+className+";";
        fullyQualifiedClassName = fullyQualifiedClassName.replace(".", "/");
        return fullyQualifiedClassName;
    }

    //read file content into a string
    public static String readFileToString(String filePath) throws IOException {
        StringBuilder fileData = new StringBuilder(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        char[] buf = new char[10];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            //	System.out.println(numRead);
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();

        return  fileData.toString();
    }
        */
}