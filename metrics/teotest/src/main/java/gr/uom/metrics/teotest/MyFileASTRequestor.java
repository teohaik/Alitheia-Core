package gr.uom.metrics.teotest;

import org.eclipse.jdt.core.dom.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyFileASTRequestor extends FileASTRequestor{

    final IBinding[] bindings = new IBinding[1];
    final CompilationUnit[] units = new CompilationUnit[1];

    public void acceptBinding(String bindingKey, IBinding binding) {
        bindings[0] = binding;
    }

    public void acceptAST(String sourceFilePath, CompilationUnit ast) {
        CompilationUnit compilationUnit = ast;
        Set names = new HashSet();
        List<AbstractTypeDeclaration> topLevelTypeDeclarations = compilationUnit.types();

        for(AbstractTypeDeclaration abstractTypeDeclaration : topLevelTypeDeclarations) {
            if(abstractTypeDeclaration instanceof TypeDeclaration) {
                TypeDeclaration topLevelTypeDeclaration = (TypeDeclaration)abstractTypeDeclaration;
                List<TypeDeclaration> typeDeclarations = new ArrayList<TypeDeclaration>();
                typeDeclarations.add(topLevelTypeDeclaration);
                TypeDeclaration[] types = topLevelTypeDeclaration.getTypes();
                for(TypeDeclaration type : types) {
                    typeDeclarations.add(type);
                }
                for(TypeDeclaration typeDeclaration : typeDeclarations) {
                    ITypeBinding typeBinding = typeDeclaration.resolveBinding();
                    if(typeBinding != null){
                        System.out.println("Type Declaration : "+typeBinding.getQualifiedName());

                        IVariableBinding[] ivb = typeBinding.getDeclaredFields();
                        System.out.println(ivb.length);
                        for(IVariableBinding ib : ivb) {
                            System.out.println("   Field declaration: " + ib.getName() );
                            System.out.println("      field type: " + ib.getType().getQualifiedName());

                        }
                    }
                }
            }
        }

    }

}
