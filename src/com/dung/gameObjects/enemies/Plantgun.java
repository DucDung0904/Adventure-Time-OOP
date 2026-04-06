package com.dung.gameObjects.enemies;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.dung.effect.Animation;
import com.dung.effect.CacheDataLoader;
import com.dung.gameObjects.GameWorld;
import com.dung.gameObjects.ParticularObject;

public class Plantgun extends ParticularObject {

	private Animation forwardAnim, backAnim;

	private long startTimeToShoot;

	public Plantgun(float x, float y, GameWorld gameWorld) {
		super(x, y, 70, 41, 0, 100, gameWorld);
		backAnim = CacheDataLoader.getInstance().getAnimation("plantgun");
        forwardAnim = CacheDataLoader.getInstance().getAnimation("plantgun");
        forwardAnim.flipAllImage();
        startTimeToShoot = 0;
        setDamage(1);

	}

	@Override
	public void attack() {
		Bullet bullet = new Bullet(getPosX() - 9, getPosY() - 8,10, getGameWorld());
        if(getDirection() == LEFT_DIR) bullet.setSpeedX(-8);
        else bullet.setSpeedX(8);
        bullet.setTeamType(getTeamType());
        getGameWorld().particularObjectManager.addObject(bullet);
	}
	

	public void Update() {
		super.Update();
		if (System.nanoTime() - startTimeToShoot > 1000 * 10000000) {
			attack();
			System.out.println("Red Eye attack");
			startTimeToShoot = System.nanoTime();
		}
	}

	@Override
	public Rectangle getBoundForCollisionWithEnemy() {
		Rectangle rect = getBoundForCollisionWithMap();
		rect.x += 20;
		rect.width -= 40;

		return rect;
	}

	@Override
	public void draw(Graphics2D g2) {
		if (!isObjectOutOfCameraView()) {
			if (getState() == NOBEHURT && (System.nanoTime() / 10000000) % 2 != 1) {
			} else {
				if (getDirection() == LEFT_DIR) {
					backAnim.Update(System.nanoTime());
					backAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
							(int) (getPosY() - getGameWorld().camera.getPosY()), g2);
				} else {
					forwardAnim.Update(System.nanoTime());
					forwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
							(int) (getPosY() - getGameWorld().camera.getPosY()), g2);
				}
			}
		}
		// drawBoundForCollisionWithEnemy(g2);
	}

}
