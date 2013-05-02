package de.hswt.hrm.plant.model;

/**
 * Represents a plant
 */
/*
 * Anlagen (Anlagenübersicht) Bezeichnung Standort Bezeichnung (Verlinkung zu den Standortdaten, der
 * entsprechende Bezeichner wird hier angezeigt z.B. Anlage Obercool) Nächste Inspektion Anzahl der
 * Elemente (ergibt sich aus der schematischen Bezeichnung) Hersteller => optional Baujahr =>
 * optional Technische Daten Typ (z.B. RMC 09/15) => optional Luftleistung (z.B. 14.000 m³/h) =>
 * optional Motorleistung (z.B. 11,0 kW) => optional Motordrehzahl (z.B. 1.355 U/min) => optional
 * Ventilatorleistung (z.B. 11,0 kW) => optional Nennstrom (z.B. 12,6 A) Spannung (z.B. 400/600 V)
 * Bemerkungen Inspektionen die für diese Anlage bereits durchgeführt wurden mit der Möglichkeit
 * Diese direkt auszuwählen (neu anlegen, kopieren bzw. öffnen), wenn noch nichts vorhanden, die
 * Möglichkeit geben eine neue Inspektion anzulegen Hier fehlen noch Informationen zu den Bauteilen
 * (z.B. Filter, Erhitzer …) siehe dazu im Bericht unter den technischen Daten Anlagen die demnächst
 * wieder mit einer Inspektion dran sind, sollten farblich gekennzeichnet werden (3 Monate vorher
 * Orange, zu spät rot), es wäre gut eine Filterung hinzuzufügen, dass z.B. alle Anlagen angezeigt
 * werden die in den nächsten N Monaten fellig sind.
 */
public class Plant {

    /**
     * Mandatory fields
     */
    private int id;
    

}
