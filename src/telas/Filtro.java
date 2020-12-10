package telas;

import java.awt.Color;
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
 * @author Taffarel Xavier4
 * @email taffarelxavier7@gmail.com
 */
public class Filtro {

    private BufferedImage bufferIn;

    /**
     * Define a imagem com o filtro cinza.
     *
     * @param buffer
     * @return
     */
    public BufferedImage setEscalaCinza(BufferedImage buffer) {
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

    /**
     * Define a imagem com o filtro de negativa.
     *
     * @param buffer
     * @return
     */
    public BufferedImage setNegativa(BufferedImage buffer) {

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

    /**
     * Define a imagem com o filtro de threshold.
     *
     * @param buffer
     * @return
     */
    public BufferedImage setThreshold(BufferedImage buffer) {
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

    /**
     *
     * @param redPercent
     * @param greenPercent
     * @param bluePercent
     * @throws IOException
     */
    public void applyColorFilter(int redPercent, int greenPercent, int bluePercent) throws IOException {

        File inputImage = new File("cachorro-original.jpg");//File path for input image
        BufferedImage image = ImageIO.read(inputImage);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);

                int alpha = (pixel >> 24) & 0xff;
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;

                pixel = (alpha << 24) | (redPercent * red / 100 << 16) | (greenPercent * green / 100 << 8) | (bluePercent * blue / 100);

                image.setRGB(x, y, pixel);
            }
        }

        ImageIO.write(image, "jpg", new File("output-cachorro-original.jpg")); //File path to store resulting image.
    }

    public BufferedImage threshold(BufferedImage image) {
        try {
            int limiar = 100;
            int width = image.getWidth();
            int height = image.getHeight();

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int rgb = image.getRGB(i, j);
                    int r = (int) ((rgb & 0x00FF0000) >>> 16);
                    int g = (int) ((rgb & 0x0000FF00) >>> 8);
                    int b = (int) (rgb & 0x000000FF);

                    int media = (r + g + b) / 3;
                    Color white = new Color(0, 75, 89);
                    Color black = new Color(0, 0, 0);

                    if (media < limiar) {
                        image.setRGB(i, j, black.getRGB());
                    } else {
                        image.setRGB(i, j, white.getRGB());
                    }
                }
            }
            return image;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Define a imagem com o filtro de Colorizacao.
     *
     * @param buffer
     * @param tipo
     * @return
     */
    public BufferedImage setColorizacao(BufferedImage buffer, int tipo) {
        int r, g, b, width = buffer.getWidth(), height = buffer.getHeight();

        BufferedImage buffer_out = new BufferedImage(width, height, buffer.getType());

        Raster raster = buffer.getRaster();

        WritableRaster wraster = buffer_out.getRaster();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                switch (tipo) {
                    case 1:
                        r = raster.getSample(x, y, 0); // red
                        g = 0;
                        b = 0;
                        break;
                    case 2:
                        r = 0;
                        g = 0;
                        b = raster.getSample(x, y, 0); // blue
                        break;
                    default:
                        r = 0;
                        g = raster.getSample(x, y, 0); // green
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

    /**
     * Define a imagem com o filtro de Sepia.
     *
     * @param buffer
     * @return
     */
    public BufferedImage setSepia(BufferedImage buffer) {
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

    /**
     * Esta função serve para carregar uma imagem. A imagem carregada servirá
     * para ser tratada
     *
     * @param arq
     * @return
     */
    public BufferedImage carregarImagem(String arq) {
        try {
            BufferedImage img = ImageIO.read(new File(arq));
            return img;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    /**
     * Este método é usado para salvar a imagem no computador.
     *
     * @param buffer
     * @param filename
     */
    public void save(BufferedImage buffer, String filename) {

        JFileChooser saveChooser = new JFileChooser();

        FileFilter imageFilter = new FileNameExtensionFilter(
                "Arquivos de imagens", ImageIO.getReaderFileSuffixes());
        saveChooser.setFileFilter(imageFilter);

        saveChooser.setSelectedFile(new File(filename));

        saveChooser.setDialogTitle("Salvar imagem copiada");

        int result = saveChooser.showSaveDialog(saveChooser);

        if (result == saveChooser.APPROVE_OPTION) { //Caso o usuário clique em confirmar.
            try {
                File saida = new File(saveChooser.getSelectedFile().getAbsolutePath());

                ImageIO.write(buffer, "jpg", saida);

                JOptionPane.showMessageDialog(null, "Imagem salva com sucesso", "Computação Gráfica I", 1);
            } catch (IOException ex) {
               
            }
        }

    }
}
