package br.edu.ifba.saj.fwads.negocio;

public class ValidarCPF {

    public static boolean validarFormatoCpf(String cpf) {
        if (cpf == null)
            return false;
        cpf = cpf.replaceAll("[^\\d]", "");
        return cpf.matches("\\d{11}");
    }

    public static String formatarCpf(String cpf) {
        cpf = cpf.replaceAll("[^\\d]", "");
        if (cpf.length() != 11)
            return cpf;
        return cpf.substring(0, 3) + "." +
                cpf.substring(3, 6) + "." +
                cpf.substring(6, 9) + "-" +
                cpf.substring(9, 11);
    }
}
