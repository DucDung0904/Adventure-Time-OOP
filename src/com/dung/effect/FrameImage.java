package com.dung.effect;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class FrameImage {

	private String name;
	private BufferedImage image;

	public FrameImage(String name, BufferedImage image) {
		this.name = name;
		this.image = image;
	}

	public FrameImage(FrameImage frameImage) {
        if (frameImage == null || frameImage.getImage() == null) {
            this.image = null;
            this.name = null;
        } else {
            this.image = new BufferedImage(frameImage.getImageWidth(), frameImage.getImageHeight(), frameImage.getImage().getType());
            Graphics g = this.image.getGraphics();
            g.drawImage(frameImage.getImage(), 0, 0, null);
            this.name = frameImage.getName();
        }
    }
	
	
	public void draw(Graphics2D g2, int x, int y) {
		g2.drawImage(image, x - image.getWidth()/2, y - image.getHeight()/2, null);
		
	}
	
	public FrameImage(){
		image = null;
		name = null;
	}

	public int getImageWidth() {
		return image.getWidth();
	}

	public int getImageHeight() {
		return image.getHeight();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

}
