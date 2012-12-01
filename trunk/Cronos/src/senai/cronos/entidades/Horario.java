package senai.cronos.entidades;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import senai.cronos.Main;
import senai.util.Tupla;

/**
 * Classe que representa um horario de uma turma. Cada turma tem um turno e um dicionario
 * de dias, em que o Dia é a chave e a unidadecurricular ministrada no momento é o elemento.
 *
 * @author sergio lisan e carlos melo
 */
public class Horario {

    /**
     * Cria uma instancia vazia padrao para Horario
     * @return horario
     */
    public static Horario create() {
        Horario horario = new Horario();        
        for (Date diaUtil : Main.CALENDARIO.getDiasUteis()) {
            horario.getHorario().put(diaUtil, new Tupla<>(Aula.PADRAO, Aula.PADRAO));
        }
        
        return horario;
    }

    /**
     * construtor vazio
     */
    private Horario() {        
    }

    public Map<Date, Tupla<Aula, Aula>> getHorario() {
        return horario;
    }

    public void setHorario(Map<Date, Tupla<Aula, Aula>> horario) {
        this.horario = horario;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Horario other = (Horario) obj;
        if (!Objects.equals(this.horario, other.horario)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.horario);
        return hash;
    }

    private Map<Date, Tupla<Aula, Aula>> horario = new TreeMap<>();
}