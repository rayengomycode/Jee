package util;

import model.Billet;
import model.Gare;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.awt.Color;

/**
 * Générateur de PDF pour les billets de train utilisant iText
 */
public class PDFGenerator {

    public static byte[] generateBilletPDF(Billet billet) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, baos);

        document.open();

        try {
            // Polices
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, Color.BLACK);
            Font headerFont = new Font(Font.HELVETICA, 14, Font.BOLD, Color.BLACK);
            Font labelFont = new Font(Font.HELVETICA, 10, Font.BOLD, Color.BLACK);
            Font normalFont = new Font(Font.HELVETICA, 10, Font.NORMAL, Color.BLACK);

            // Titre principal
            Paragraph title = new Paragraph("BILLET DE TRAIN", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(10);
            document.add(title);

            Paragraph subtitle = new Paragraph("Train Ticket System", headerFont);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            subtitle.setSpacingAfter(20);
            document.add(subtitle);

            // Ligne de séparation
            Paragraph separator = new Paragraph("_".repeat(80), normalFont);
            separator.setAlignment(Element.ALIGN_CENTER);
            document.add(separator);
            document.add(Chunk.NEWLINE);

            // Informations du billet
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setSpacingAfter(10);

            // Informations générales
            addTableRow(table, "Numéro de billet:", billet.getId().toString(), labelFont, normalFont);
            addTableRow(table, "Passager:", billet.getUser().getPrenom() + " " + billet.getUser().getNom(), labelFont, normalFont);

            if (billet.getVoyage() != null && billet.getVoyage().getTrain() != null) {
                addTableRow(table, "Train:", billet.getVoyage().getTrain().getNumero() + " - " + billet.getVoyage().getTrain().getNom(), labelFont, normalFont);
            }

            // Informations du voyage
            if (billet.getVoyage() != null && billet.getVoyage().getTrajet() != null) {
                Gare gareDepart = billet.getVoyage().getTrajet().getGareDepart();
                Gare gareArrivee = billet.getVoyage().getTrajet().getGareArrivee();

                if (gareDepart != null) {
                    addTableRow(table, "Gare de départ:", gareDepart.getNom() + " (" + gareDepart.getVille() + ")", labelFont, normalFont);
                }
                if (gareArrivee != null) {
                    addTableRow(table, "Gare d'arrivée:", gareArrivee.getNom() + " (" + gareArrivee.getVille() + ")", labelFont, normalFont);
                }

                // Dates et heures
                if (billet.getVoyage().getDate() != null) {
                    addTableRow(table, "Date de voyage:", billet.getVoyage().getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), labelFont, normalFont);
                }
                if (billet.getVoyage().getHeureDepart() != null) {
                    addTableRow(table, "Heure de départ:", billet.getVoyage().getHeureDepart().format(DateTimeFormatter.ofPattern("HH:mm")), labelFont, normalFont);
                }
                if (billet.getVoyage().getHeureArrivee() != null) {
                    addTableRow(table, "Heure d'arrivée:", billet.getVoyage().getHeureArrivee().format(DateTimeFormatter.ofPattern("HH:mm")), labelFont, normalFont);
                }
            }

            // Informations du billet
            if (billet.getClasse() != null) {
                addTableRow(table, "Classe:", billet.getClasse().getNom(), labelFont, normalFont);
            }
            addTableRow(table, "Siège:", billet.getNumeroSiege() != null ? billet.getNumeroSiege() : "Non assigné", labelFont, normalFont);
            addTableRow(table, "Prix:", String.format("%.2f TND", billet.getPrix()), labelFont, normalFont);

            if (billet.getPreferences() != null && !billet.getPreferences().isEmpty()) {
                addTableRow(table, "Préférences:", billet.getPreferences(), labelFont, normalFont);
            }

            // Informations d'achat
            if (billet.getDateAchat() != null) {
                addTableRow(table, "Date d'achat:", billet.getDateAchat().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), labelFont, normalFont);
            }
            addTableRow(table, "État:", billet.getEtat().toString(), labelFont, normalFont);

            document.add(table);

            // Code QR simulé
            document.add(Chunk.NEWLINE);
            Paragraph qrTitle = new Paragraph("Code de validation", headerFont);
            qrTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(qrTitle);

            Paragraph qrCode = new Paragraph("QR-" + billet.getId(), normalFont);
            qrCode.setAlignment(Element.ALIGN_CENTER);
            qrCode.setSpacingAfter(10);
            document.add(qrCode);

            // Instructions
            Paragraph instructions = new Paragraph("Présentez ce billet lors du contrôle", new Font(Font.HELVETICA, 8, Font.ITALIC, Color.GRAY));
            instructions.setAlignment(Element.ALIGN_CENTER);
            document.add(instructions);

            // Pied de page
            document.add(Chunk.NEWLINE);
            Paragraph footerSeparator = new Paragraph("_".repeat(80), normalFont);
            footerSeparator.setAlignment(Element.ALIGN_CENTER);
            document.add(footerSeparator);

            Paragraph footer = new Paragraph("Train Ticket System - Billet généré le " +
                java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                new Font(Font.HELVETICA, 8, Font.NORMAL, Color.GRAY));
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

        } finally {
            document.close();
        }

        return baos.toByteArray();
    }

    private static void addTableRow(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setPaddingBottom(5);
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setPaddingBottom(5);
        table.addCell(valueCell);
    }
}
