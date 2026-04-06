package com.dung.gameObjects;

import java.awt.Rectangle;


public abstract class Human extends ParticularObject{
	
    private boolean isJumping;
    
    private boolean isLanding;
        
    
    public Human(float x, float y, float width, float height, float mass, int blood, GameWorld gameWorld) {
        super(x, y, width, height, mass, 3, gameWorld);
        setState(ALIVE);
        
       
    }

    public abstract void Run();
    
    public abstract void Jump();
    
    public abstract void StandUp();
    
    public abstract void StopRun();
    

    public boolean isJumping() {
        return isJumping;
    }
    
    public void setLanding(boolean b){
        isLanding = b;
    }
    
    public boolean isLanding(){
        return isLanding;
    }
    
    public void setJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }
    

	@Override
    public void Update(){
        
        super.Update();
        
        if(getState() == ALIVE || getState() == NOBEHURT){
        
            if(!isLanding){

                setPosX(getPosX() + getSpeedX());

                if(getDirection() == LEFT_DIR && 
                        getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap())!=null){

                    Rectangle rectLeftWall = getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap());
                    setPosX(rectLeftWall.x + rectLeftWall.width + getWidth()/2);

                }
                if(getDirection() == RIGHT_DIR && 
                        getGameWorld().physicalMap.haveCollisionWithRightWall(getBoundForCollisionWithMap())!=null){

                    Rectangle rectRightWall = getGameWorld().physicalMap.haveCollisionWithRightWall(getBoundForCollisionWithMap());
                    setPosX(rectRightWall.x - getWidth()/2);

                }

                Rectangle boundForCollisionWithMapFuture = getBoundForCollisionWithMap();
                boundForCollisionWithMapFuture.y += (getSpeedY()!=0?getSpeedY(): 2);
                Rectangle rectLand = getGameWorld().physicalMap.haveCollisionWithLand(boundForCollisionWithMapFuture);
                
                Rectangle rectTop = getGameWorld().physicalMap.haveCollisionWithTop(boundForCollisionWithMapFuture);
                
                if(rectTop !=null){
                    
                    setSpeedY(0);
                    setPosY(rectTop.y + getGameWorld().physicalMap.getTileSize() + getHeight()/2);
                    
                }else if(rectLand != null){
                    setJumping(false);
                    if(getSpeedY() > 0) setLanding(true);
                    setSpeedY(0);
                    setPosY(rectLand.y - getHeight()/2 - 1);
                }else{
                    setJumping(true);
                    setSpeedY(getSpeedY() + getMass());
                    setPosY(getPosY() + getSpeedY());
                }
            }
        }
    }
}
