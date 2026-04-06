package com.dung.gameObjects.enemies;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.dung.effect.Animation;
import com.dung.effect.CacheDataLoader;
import com.dung.gameObjects.GameWorld;
import com.dung.gameObjects.ParticularObject;

public class Bullet extends ParticularObject {

	private Animation forwardBulletAnim, backBulletAnim;
	
	public Bullet(float x, float y,int damage, GameWorld gameWorld) {
		super(x, y, 17, 10, 0.1f, 10, gameWorld);
		forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("bullet");
        backBulletAnim = CacheDataLoader.getInstance().getAnimation("bullet");
        backBulletAnim.flipAllImage();
        setDamage(1);
		
	}
	@Override
    public Rectangle getBoundForCollisionWithEnemy() {
         
            return getBoundForCollisionWithMap();
    }

    @Override
    public void draw(Graphics2D g2) {
          
        if(getSpeedX() > 0){          
            forwardBulletAnim.Update(System.nanoTime());
            forwardBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }else{
            backBulletAnim.Update(System.nanoTime());
            backBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }
        //drawBoundForCollisionWithEnemy(g2);
    }

    @Override
    public void Update() {
        super.Update();
        setPosX(getPosX() + getSpeedX());
        setPosY(getPosY() + getSpeedY());
        ParticularObject object = getGameWorld().particularObjectManager.getCollisionWithEnemyObject(this);
        if(object!=null && object.getState() == ALIVE){           
            object.beHurt(getDamage());
            setState(DEATH);
            System.out.println("Bullet set behurt for enemy");
        
         
        }
    } 

    @Override
    public void attack() {}

}

