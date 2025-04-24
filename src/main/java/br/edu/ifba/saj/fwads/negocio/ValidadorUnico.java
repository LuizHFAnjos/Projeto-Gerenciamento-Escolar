package br.edu.ifba.saj.fwads.negocio;

import java.util.List;
import java.util.function.Function;

public class ValidadorUnico {

    public static <T> boolean valorJaExiste(String valor, List<T> lista, Function<T, String> getter) {
        return lista.stream().anyMatch(obj -> getter.apply(obj).equalsIgnoreCase(valor));
    }

}
