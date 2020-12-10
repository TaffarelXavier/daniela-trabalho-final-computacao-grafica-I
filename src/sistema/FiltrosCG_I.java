package sistema;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 
 * @author Dani
 */
public class FiltrosCG_I {
    
    public BufferedImage filtroNegativa(BufferedImage buffer) {

        int r, g, b, width = buffer.getWidth(), height = buffer.getHeight();

        BufferedImage buffer_out = new BufferedImage(width, height, buffer.getType());

        Raster raster = buffer.getRaster();

        WritableRaster wraster = buffer_out.getRaster();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                r = raster.getSample(x, y, 0);

                g = raster.getSample(x, y, 1);

                b = raster.getSample(x, y, 2);

                wraster.setSample(x, y, 0, 255 - r);

                wraster.setSample(x, y, 1, 255 - g);

                wraster.setSample(x, y, 2, 255 - b);
            }
        }
        return buffer_out;
    }

    public BufferedImage filtroCinza(BufferedImage buffer) {
        int r, g, b, width = buffer.getWidth(), height = buffer.getHeight();

        BufferedImage buffer_out = new BufferedImage(width, height, buffer.getType());

        Raster raster = buffer.getRaster();

        WritableRaster wraster = buffer_out.getRaster();

        for (int y = 0; y < height; y++) {

            for (int x = 0; x < width; x++) {

                r = raster.getSample(x, y, 0);

                g = raster.getSample(x, y, 1);

                b = raster.getSample(x, y, 2);

                int avg = (r + g + b) / 3;

                wraster.setSample(x, y, 0, avg);

                wraster.setSample(x, y, 1, avg);

                wraster.setSample(x, y, 2, avg);
            }
        }
        return buffer_out;
    }

    public BufferedImage filtroColorizacao(BufferedImage buffer, int tipo) {
        int r, g, b, width = buffer.getWidth(), height = buffer.getHeight();

        BufferedImage buffer_out = new BufferedImage(width, height, buffer.getType());

        Raster raster = buffer.getRaster();

        WritableRaster wraster = buffer_out.getRaster();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                switch (tipo) {
                    case 1:
                        r = raster.getSample(x, y, 0);
                        g = 0;
                        b = 0;
                        break;
                    case 2:
                        r = 0;
                        g = 0;
                        b = raster.getSample(x, y, 0);
                        break;
                    default:
                        r = 0;
                        g = raster.getSample(x, y, 0);
                        b = 0;
                        break;
                }

                wraster.setSample(x, y, 0, r);
                wraster.setSample(x, y, 1, g);
                wraster.setSample(x, y, 2, b);

            }
        }
        return buffer_out;
    }

    public BufferedImage filtroSepia(BufferedImage buffer) {
        double r, g, b, novoRed, novoGreen, novoBlue;

        int width = buffer.getWidth(), height = buffer.getHeight();

        BufferedImage buffer_out = new BufferedImage(width, height, buffer.getType());

        Raster raster = buffer.getRaster();

        WritableRaster wraster = buffer_out.getRaster();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                r = raster.getSample(x, y, 0);
                g = raster.getSample(x, y, 1);
                b = raster.getSample(x, y, 2);

                novoRed = 0.393 * r + 0.769 * g + 0.189 * b;
                novoGreen = 0.349 * r + 0.686 * g + 0.168 * b;
                novoBlue = 0.272 * r + 0.534 * g + 0.131 * b;

                if (novoRed > 255) {
                    r = 255;
                } else {
                    r = novoRed;
                }

                if (novoGreen > 255) {
                    g = 255;
                } else {
                    g = novoGreen;
                }

                if (novoBlue > 255) {
                    b = 255;
                } else {
                    b = novoBlue;
                }

                wraster.setSample(x, y, 0, r);
                wraster.setSample(x, y, 1, g);
                wraster.setSample(x, y, 2, b);
            }
        }
        return buffer_out;
    }

    public BufferedImage filtroThreshold(BufferedImage buffer) {
        int r, g, b, width = buffer.getWidth(), height = buffer.getHeight();

        BufferedImage buffer_out = new BufferedImage(width, height, buffer.getType());

        Raster raster = buffer.getRaster();

        WritableRaster wraster = buffer_out.getRaster();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                r = raster.getSample(x, y, 0);

                g = raster.getSample(x, y, 1);

                b = raster.getSample(x, y, 2);

                int avg = (r + g + b) / 3;

                int Limiar = 100;

                int pixel = 255;

                if (avg < Limiar) {
                    pixel = 0;
                }
                wraster.setSample(x, y, 0, pixel);
                wraster.setSample(x, y, 1, pixel);
                wraster.setSample(x, y, 2, pixel);
            }
        }
        return buffer_out;
    }

    public BufferedImage carregarImagem(String arq) {
        try {
            BufferedImage img = ImageIO.read(new File(arq));
            return img;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void salvarArquivoNoDisco(BufferedImage buffer, String filename) {

        JFileChooser saveChooser = new JFileChooser();

        FileFilter imageFilter = new FileNameExtensionFilter(
                "Arquivos de imagens", ImageIO.getReaderFileSuffixes());
        saveChooser.setFileFilter(imageFilter);

        saveChooser.setSelectedFile(new File(filename));

        int result = saveChooser.showSaveDialog(saveChooser);

        if (result == saveChooser.APPROVE_OPTION) { //Caso o usuário clique em confirmar.
            try {
                File saida = new File(saveChooser.getSelectedFile().getAbsolutePath());

                ImageIO.write(buffer, "jpg", saida);

                JOptionPane.showMessageDialog(null, "Imagem salva com sucesso", "Sucesso", 1);
            } catch (IOException ex) {

            }
        }

    }
}
