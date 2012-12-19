/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package senai.cronos.horario;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import senai.cronos.Main;
import senai.cronos.entidades.Aula;
import senai.cronos.entidades.Turno;
import senai.util.Tupla;

/**
 *
 * @author Sergio
 */
public class HorarioDocente {

    private HorarioDocente(Map<Date, Map<Turno, Tupla<Aula, Aula>>> horario) {
        this.horario = horario;
    }

    /**
     * Gera uma estrutura vazia, pronta para receber dados
     *
     * @return horarioDocente
     */
    public static HorarioDocente create() {
        Map<Date, Map<Turno, Tupla<Aula, Aula>>> horario = new HashMap<>();

        for (Date dia : Main.CALENDARIO.getDiasUteis()) {
            Map<Turno, Tupla<Aula, Aula>> diaDeTrabalho = new HashMap<>();
            diaDeTrabalho.put(Turno.MANHA, new Tupla<>(Aula.PADRAO, Aula.PADRAO));
            diaDeTrabalho.put(Turno.TARDE, new Tupla<>(Aula.PADRAO, Aula.PADRAO));
            diaDeTrabalho.put(Turno.NOITE, new Tupla<>(Aula.PADRAO, Aula.PADRAO));
            horario.put(dia, diaDeTrabalho);
        }

        return new HorarioDocente(horario);
    }

    /**
     * Cria uma ocupação para o docente
     *
     * @param dia
     * @param turno
     * @param aula
     * @param half
     */
    public void add(Date dia, Turno turno, Aula aula, Integer half) {
        horario.get(dia).get(turno).set(aula, half);
    }

    /**
     * Remove uma aula para o docente
     * @param dia
     * @param turno
     * @param half
     */
    public void remove(Date dia, Turno turno, Integer half) {
        horario.get(dia).get(turno).set(Aula.PADRAO, half);
    }

    /**
     * Retorna uma aula em um determinado momento do dia
     * @param dia
     * @param turno
     * @param half
     * @return
     */
    public Aula getAula(Date dia, Turno turno, Integer half) {
        return (Aula) horario.get(dia).get(turno).get(half);
    }

    /**
     * Retorna o horário
     *
     * @return horario
     */
    public Map<Date, Map<Turno, Tupla<Aula, Aula>>> getHorario() {
        return horario;
    }

    /**
     * Calcula a percentagem de ocupação do docente
     * @return
     */
    public double getPercentualOcupacao() {
        int ocupacao = 0;
        int slots = horario.keySet().size() * 2 * 2;

        for (Date dia : horario.keySet() ) {
            for (Turno turno : horario.get(dia).keySet() ) {
                Tupla<Aula, Aula> aulas = horario.get(dia).get(turno);
                if (!aulas.get(Tupla.PRIMEIRA).equals(Aula.PADRAO) )
                    ocupacao++;

                if (!aulas.get(Tupla.SEGUNDA).equals(Aula.PADRAO) )
                    ocupacao++;
            }
        }

        return (ocupacao * 100) / slots;
    }

    /**
     * Verifica se o docente ja possui uma ocupacao em determinado horario
     * @param tn
     * @param dia
     * @param metade
     * @return
     */
    public boolean isDisponivel(Date dia, Turno tn, Integer metade) {
        return getAula(dia, tn, metade).equals(Aula.PADRAO);
    }

    /**
     * Verifica disponibilidade de um docente
     * @param dia
     * @param tn
     * @return
     */
    public boolean isDisponivel(Date dia, Turno tn) {
        return ( getAula(dia, tn, Tupla.PRIMEIRA).equals(Aula.PADRAO) &&
                 getAula(dia, tn, Tupla.SEGUNDA) .equals(Aula.PADRAO) );
    }

    /**
     * Estrutura de dados que armazena o horario do docente
     */
    private Map<Date, Map<Turno, Tupla<Aula, Aula>>> horario;
}