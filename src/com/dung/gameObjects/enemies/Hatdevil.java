package com.dung.gameObjects.enemies;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.dung.effect.Animation;
import com.dung.effect.CacheDataLoader;
import com.dung.gameObjects.GameWorld;
import com.dung.gameObjects.ParticularObject;
import com.dung.gameObjects.PhysicalMap;

public class Hatdevil extends ParticularObject {

	private Animation forwardAnim, backAnim;
	
    private float range; 
    private float startPosX; 

    public Hatdevil(float x, float y, float range, GameWorld gameWorld) {
        super(x, y, 40, 40, 1.0f, 100, gameWorld);
        
        forwardAnim = CacheDataLoader.getInstance().getAnimation("hatdevil");
        backAnim = CacheDataLoader.getInstance().getAnimation("hatdevil");
        backAnim.flipAllImage();
        
        this.range = range;
        this.startPosX = x;
        setSpeedX(2);
        setTeamType(ParticularObject.ENEMY_TEAM);
        setDirection(ParticularObject.RIGHT_DIR);
        setDamage(1);
    }

    @Override
    public void Update() {
        super.Update();

        PhysicalMap physicalMap = getGameWorld().physicalMap;
        Rectangle bound = getBoundForCollisionWithMap();

        
        if (physicalMap.haveCollisionWithLeftWall(bound) != null || 
            physicalMap.haveCollisionWithRightWall(bound) != null) {
            reverseDirection();
        }

        if (getPosX() > startPosX + range || getPosX() < startPosX - range) {
            reverseDirection();
        }


        setPosX(getPosX() + getSpeedX());
    }

    private void reverseDirection() {
        setSpeedX(-getSpeedX());
        setDirection(getDirection() == ParticularObject.LEFT_DIR ? ParticularObject.RIGHT_DIR : ParticularObject.LEFT_DIR);
        
        if (getDirection() == ParticularObject.LEFT_DIR) {
            // Đổi hướng trái
            forwardAnim.reset();
        } else {
            // Đổi hướng phải
            forwardAnim.reset();
        }
    }

    @Override
    public void attack() {}

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        return getBoundForCollisionWithMap();
    }

    @Override
    public void draw(Graphics2D g2) {
    	if (!isObjectOutOfCameraView()) {
			if (getState() == NOBEHURT && (System.nanoTime() / 10000000) % 2 != 1) {
				// plash...
			} else {
				if (getDirection() == RIGHT_DIR) {
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