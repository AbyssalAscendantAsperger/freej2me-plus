/*
	This file is part of FreeJ2ME.

	FreeJ2ME is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	FreeJ2ME is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with FreeJ2ME.  If not, see http://www.gnu.org/licenses/
*/
package com.nttdocomo.ui;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.ImageWriteParam;
import java.util.Iterator;

public class ImageEncoder 
{
    public static final int ATTR_QUALITY_HIGH = 0;
    public static final int ATTR_QUALITY_STANDARD = 1;
    public static final int ATTR_QUALITY_LOW = 2;
    public static final int QUALITY = 0;

    private int quality = ATTR_QUALITY_STANDARD;

    public static ImageEncoder getEncoder(String format) 
    {
        if (format == null) { throw new NullPointerException("Format cannot be null"); }

        return new ImageEncoder();
    }

    public EncodedImage encode(Canvas canvas, int x, int y, int width, int height) 
    {
        if (canvas == null) { throw new NullPointerException("Canvas cannot be null"); }
        if (width <= 0 || height <= 0) { throw new IllegalArgumentException("Width and height must be greater than 0"); }

        BufferedImage bufferedImage = new BufferedImage(canvas.platformImage.getWidth(), canvas.platformImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(canvas.platformImage.getCanvas(), 0, 0, null);
        g2d.dispose();

        BufferedImage croppedImage = bufferedImage.getSubimage(x, y, width, height);
        
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try 
        {
            encodeAsJpeg(croppedImage, stream);
            return new EncodedImage(stream.toByteArray());
        } 
        catch (IOException e) { throw new RuntimeException("Error encoding image: " + e.getMessage(), e); } 
        finally 
        { 
            try { stream.close(); }
            catch (Exception e) {}
        }
    }

    public EncodedImage encode(Image img, int x, int y, int width, int height) 
    {
        if (img == null) { throw new NullPointerException("Image cannot be null"); }
        if (width <= 0 || height <= 0) { throw new IllegalArgumentException("Width and height must be greater than 0"); }

        BufferedImage bufferedImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(img.getCanvas(), 0, 0, null);
        g2d.dispose();

        BufferedImage croppedImage = bufferedImage.getSubimage(x, y, width, height);
        
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try 
        {
            encodeAsJpeg(croppedImage, stream);
            return new EncodedImage(stream.toByteArray());
        } 
        catch (IOException e) { throw new RuntimeException("Error encoding image: " + e.getMessage(), e); } 
        finally 
        { 
            try { stream.close(); }
            catch (Exception e) {}
        }
    }

    private void encodeAsJpeg(BufferedImage image, ByteArrayOutputStream stream) throws IOException 
    {
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
        if (!writers.hasNext()) { throw new RuntimeException("No writer found for JPEG format"); }

        ImageWriter writer = writers.next();
        ImageWriteParam param = writer.getDefaultWriteParam();

        float qualityValue = jpegQuality(quality);
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(qualityValue);

        writer.setOutput(ImageIO.createImageOutputStream(stream));
        writer.write(null, new javax.imageio.IIOImage(image, null, null), param);
        writer.dispose();
    }

    private float jpegQuality(int quality) 
    {
        switch (quality) 
        {
            case ATTR_QUALITY_HIGH:
                return 1.0f; 
            case ATTR_QUALITY_STANDARD:
                return 0.75f; 
            case ATTR_QUALITY_LOW:
                return 0.5f; 
            default:
                return 0.75f; 
        }
    }

    public void setAttribute(int attr, int value) 
    {
        if (attr == QUALITY) 
        {
            if (value < ATTR_QUALITY_LOW || value > ATTR_QUALITY_HIGH) { throw new IllegalArgumentException("Invalid quality value"); }
            this.quality = value;
        }
    }

    public boolean isAvailable(int attr) { return attr == QUALITY; }
}