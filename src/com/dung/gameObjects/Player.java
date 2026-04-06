package com.dung.gameObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.dung.effect.Animation;
import com.dung.effect.CacheDataLoader;

public class Player extends Human {
   
    private Animation runForwardAnim, runBackAnim;
    private Animation idleForwardAnim, idleBackAnim;
    private Animation dickForwardAnim, dickBackAnim;
    private Animation jumpForwardAnim, jumpBackAnim;
    private Animation landingForwardAnim, landingBackAnim;   
    
    public Player(float x, float y, GameWorld gameWorld) {
        super(x, y, 38, 52, 0.1f, 3, gameWorld); 
        
        setTeamType(LEAGUE_TEAM);

        setTimeForNoBehurt(2000*1000000);
        
        runForwardAnim = CacheDataLoader.getInstance().getAnimation("run");
        runBackAnim = CacheDataLoader.getInstance().getAnimation("run");
        runBackAnim.flipAllImage();   
        
        idleForwardAnim = CacheDataLoader.getInstance().getAnimation("idle");
        idleBackAnim = CacheDataLoader.getInstance().getAnimation("idle");
        idleBackAnim.flipAllImage();
  
        
        jumpForwardAnim = CacheDataLoader.getInstance().getAnimation("jump");
        jumpForwardAnim.setRepeated(false);
        jumpBackAnim = CacheDataLoader.getInstance().getAnimation("jump");
        jumpBackAnim.setRepeated(false);
        jumpBackAnim.flipAllImage();
        
        landingForwardAnim = CacheDataLoader.getInstance().getAnimation("landing");
        landingBackAnim = CacheDataLoader.getInstance().getAnimation("landing");
        landingBackAnim.flipAllImage();
       
        
        deathForwardAnim = CacheDataLoader.getInstance().getAnimation("death");
        deathBackAnim = CacheDataLoader.getInstance().getAnimation("death");
        deathBackAnim.flipAllImage();
       
    }
    
	@Override
    public void Update() {

        super.Update();
  
        // Kiểm tra Player ra ngoài giới hạn bản đồ
        PhysicalMap physicalMap = getGameWorld().physicalMap;
        if (getPosX() > physicalMap.getTileSize() * physicalMap.phys_map[0].length ||
             getPosY() > physicalMap.getTileSize() * physicalMap.phys_map.length) {
        	
        	setState(FEY);
            setBlood(0);
            
            
        }
        
        if(isLanding()){
            landingBackAnim.Update(System.nanoTime());
            if(landingBackAnim.isLastFrame()) {
                setLanding(false);
                landingBackAnim.reset();
                runForwardAnim.reset();
                runBackAnim.reset();
            }
        }
        
    }
	

    private void drawHealthBar(Graphics2D g2) {
        int barWidth = 50; // độ rộng thanh máu
        int barHeight = 5; // chiều cao thanh máu
        int x = (int) (getPosX() - getGameWorld().camera.getPosX() - 25); // vị trí thanh máu theo X
        int y = (int) (getPosY() - getGameWorld().camera.getPosY() - 20); // vị trí thanh máu theo Y

        // Vẽ thanh máu
        g2.setColor(Color.BLACK);
        g2.fillRect(x, y, barWidth, barHeight);
        g2.setColor(Color.RED);
        g2.fillRect(x, y, (int) ((getBlood() / 3.0) * barWidth), barHeight); // tính phần thanh máu theo tỷ lệ
    }
   

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
	
        Rectangle rect = getBoundForCollisionWithMap();       
            rect.x = (int) getPosX() - 19;
            rect.y = (int) getPosY() - 26;
            rect.width = 38;
            rect.height = 52;
       
        return rect;
        
        
    }

    @Override
    public void draw(Graphics2D g2) {
        
    	// Vẽ các animation của player theo state
    	switch(getState()){
        
    	case ALIVE:
        case NOBEHURT:
            if(getState() == NOBEHURT && (System.nanoTime()/10000000)%2!=1)
            {
                System.out.println("Plash...");
            }else{          
                
                if(isLanding()){

                    if(getDirection() == RIGHT_DIR){
                        landingForwardAnim.setCurrentFrame(landingBackAnim.getCurrentFrame());
                        landingForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                                (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getBoundForCollisionWithMap().height/2 - landingForwardAnim.getCurrentImage().getHeight()/2),
                                g2);
                    }else{
                        landingBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                                (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getBoundForCollisionWithMap().height/2 - landingBackAnim.getCurrentImage().getHeight()/2),
                                g2);
                    }

                }else if(isJumping()){

                    if(getDirection() == RIGHT_DIR){
                    	jumpForwardAnim.Update(System.nanoTime());
                        
                    	jumpForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                    }else{
                    	jumpBackAnim.Update(System.nanoTime());
                        
                    	jumpBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                    }
                 }else {
                    if(getSpeedX() > 0){
                        runForwardAnim.Update(System.nanoTime());
                        
                            runForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                        if(runForwardAnim.getCurrentFrame() == 1) runForwardAnim.setIgnoreFrame(0);
                    }else if(getSpeedX() < 0){
                        runBackAnim.Update(System.nanoTime());
                            runBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                        if(runBackAnim.getCurrentFrame() == 1) runBackAnim.setIgnoreFrame(0);
                    }else{
                        if(getDirection() == RIGHT_DIR){
                            
                                idleForwardAnim.Update(System.nanoTime());
                                idleForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            }
                        else{
                
                                idleBackAnim.Update(System.nanoTime());
                                idleBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            }
                        }
                    }            
                 
            }      
            break;
        		  
        		case FEY:
       
        			if(getDirection() == RIGHT_DIR) {
        				deathForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        			}else{
        				deathBackAnim.setCurrentFrame(behurtForwardAnim.getCurrentFrame());
        				deathBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        			} 
        			break; 
        			
    	}	
    	 // Vẽ thanh máu
        drawHealthBar(g2);
        //drawBoundForCollisionWithMap(g2);
        //drawBoundForCollisionWithEnemy(g2);
    
}

    @Override
    public void Run() {
        if(getDirection() == LEFT_DIR)
            setSpeedX(-3);
        else setSpeedX(3);
    }

    @Override
    public void Jump() {

    	if(!isJumping()){
            setJumping(true);
            setSpeedY(-5.5f);           
           
        }
       
    }

    

    @Override
    public void StandUp() {
        idleForwardAnim.reset();
        idleBackAnim.reset();
     
    }

    @Override
    public void StopRun() {
        setSpeedX(0);
        runForwardAnim.reset();
        runBackAnim.reset();
        runForwardAnim.unIgnoreFrame(0);
        runBackAnim.unIgnoreFrame(0);
        
     
   
    }

    @Override
    public void attack() {}
}
