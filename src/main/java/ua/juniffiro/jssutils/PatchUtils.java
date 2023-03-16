package ua.juniffiro.jssutils;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * +-+-+-+-+-+ +-+-+ +-+-+-+-+-+-+-+-+-+
 * ( Created ) ( by ) ( @juniffiro )
 * 27/02/2023
 * +-+-+-+-+-+ +-+-+ +-+-+-+-+-+-+-+-+-+
 */
public class PatchUtils {

    /**
     * Get a method by name and data type.
     * @param methodName
     *        The name of the method you want to get
     * @param returnType
     *        The type of data returned by the method
     * @param ctClass
     *        CtClass from which the method
     * <p>
     * If the method is not found, null will be returned.
     * NPE can be thrown.
     *
     * @return {@link javassist.CtMethod}
     */
    public static CtMethod getMethodByReturn(String methodName, String returnType, CtClass ctClass) {
        try {
            for (CtMethod cm : ctClass.getDeclaredMethods()) {
                if (cm.getName().equals(methodName)) {
                    if (cm.getReturnType().getName().equals(returnType)) {
                        return cm;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Verify that the method signature matches.
     * Comparison of arguments.
     * @param cm
     *        CtMethod.
     * @param args
     *        Arguments of the method you need
     *
     * @return Does the signature match.
     */
    public static boolean checkSignature(CtMethod cm, String... args) {
        int size = 0;
        boolean checkSign = false;
        try {
            size = cm.getParameterTypes().length;
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        if (size != args.length) {
            System.out.printf("Method %s not equals args size.%n", cm.getName());
            return false;
        }
        StringBuilder methodSignature = new StringBuilder();
        methodSignature.append(cm.getName());
        methodSignature.append("(");
        for (int i = 0; i < args.length; i++) {
            String c = args[i];
            methodSignature.append(c);
            if (i != args.length - 1) {
                methodSignature.append(",");
            }
        }
        methodSignature.append(")");
        try {
            String sign0 = getJavaMethodSignature(cm);
            String sign1 = methodSignature.toString();
            System.out.println("sign0: " + sign0);
            System.out.println("sign1: " + methodSignature);
            if (sign1.equals(sign0)) {
                checkSign = true;
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return checkSign;
    }

    /**
     * Get CtMethod's signature.
     *
     * @throws NotFoundException
     *         If the method was not found
     */
    public static String getJavaMethodSignature(CtMethod ctMethod) throws NotFoundException {
        StringBuilder methodSignature = new StringBuilder();
        methodSignature.append(ctMethod.getName());
        methodSignature.append("(");
        for (int i = 0; i < ctMethod.getParameterTypes().length; i++) {
            methodSignature.append(ctMethod.getParameterTypes()[i].getName());
            if (i != ctMethod.getParameterTypes().length - 1) {
                methodSignature.append(",");
            }
        }
        methodSignature.append(")");
        return methodSignature.toString();
    }

    /**
     * Get a method by name, data type and args.
     *
     * @param methodName
     *        The name of the method you want to get
     * @param returnType
     *        The type of data returned by the method
     * @param ctClass
     *        CtClass from which the method
     * @param args
     *        Arguments of the method to get
     * <p>
     * If the method is not found, null will be returned.
     * NPE can be thrown.
     *
     * @return {@link javassist.CtMethod}
     */
    public static CtMethod getMethodByParams(String methodName, String returnType, CtClass ctClass, String... args) {
        try {
            for (CtMethod cm : ctClass.getDeclaredMethods()) {
                if (cm.getName().equals(methodName) && cm.getReturnType().getName().equals(returnType)) {
                    if (checkSignature(cm, args)) {
                        return cm;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static boolean insertMethodBefore(CtMethod ctMethod,
                                             String src) {
        boolean insert = false;
        try {
            ctMethod.insertBefore(src);
            insert = true;
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
        return insert;
    }

    /**
     * Replace an existing method with the same name with a static method.
     * @param ctClass
     * @param methodName
     */
    public static void emptyStaticMethod(CtClass ctClass, String methodName) {
        try {
            CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);
            ctClass.removeMethod(ctMethod);
            CtMethod make = CtMethod.make(
                    CodeMaker.makeCode("public static void " + methodName + "()",
                    "{", "}"
            ).toString(), ctClass);
            ctClass.addMethod(make);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean setBody(CtMethod ctMethod,
                                  String src) {
        boolean insert = false;
        try {
            ctMethod.setBody(src);
            insert = true;
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
        return insert;
    }
}
