import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends Actor
{
    private int vSpeed = -10;
    private int acceleration = 1;
    private int jumpHeight = -10;
    private boolean isJumping = false;
    private int jumpCount = 0;

    int _idleImageNum= 4;
    int _walksImageNum= 7;
    int _jumpHeightImageNum= 6;
    int _landingImageNum= 4;

    String state = "idle";
    public String getState()
    {
        return this.state;
    }
    
    public void setState(String state)
    {
        this.state = state;
    }
    
     public Player(){
        this.setImage(new GreenfootImage("/player/idle/0.png"));
    }
    int _animationDelay = 10;
    int _animationTimer = 0;
    int _lastSpriteNo = 0;
    
    Boolean moveFoward = true;
     void animate()
    {
        if(_animationTimer<_animationDelay)
        {
            _animationTimer++;
            return;
        }
        _animationTimer = 0;
        String path = "";
        switch(this.state)
        {
            case "idle" : {path = "/player/idle/"+_lastSpriteNo%_idleImageNum+".png"; break;}
            case "walks": {path = "/player/walks/"+_lastSpriteNo%_walksImageNum+".png"; break;}
            case "jumpHeight": {path = "/player/jumpHeight/"+_lastSpriteNo%_jumpHeightImageNum+".png"; break;}
            case "landing": {path = "/player/landing/"+_lastSpriteNo%_landingImageNum+".png"; break;}
            default: {path ="/player/idle/0.png"; break;}
        }
        _lastSpriteNo++;
        GreenfootImage img = new GreenfootImage(path);
        if(moveFoward)
        {
            this.setImage(img);
            return;
        }
        img.mirrorHorizontally();
        this.setImage(img);
    }
    
     void controlPlayer()
    {
        if(Greenfoot.isKeyDown("right"))
        {
            this.moveFoward = true;
            this.state = "walks";
            this.setLocation(this.getX()+5, this.getY());
            return;
        }
        if(Greenfoot.isKeyDown("left"))
        {
            this.moveFoward = false;
            this.state = "walks";
            this.setLocation(this.getX()-5, this.getY());
            return;
        }
        if(Greenfoot.isKeyDown("space"))
        {
            vSpeed = jumpHeight;
            fall();
            this.setLocation(this.getX()+2, this.getY()-2);
            this.state = "jumpHeight";
        }
        
        if(isJumping)
        {
            jumpCount++;
        }
        if(jumpCount < 10) 
        {
            isJumping = false;
            jumpCount = 0;
        }
        
        this.state = "landing";
        this.state = "idle";
    }
    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        controlPlayer();
        animate();
        moveAround();
        checkFalling();

        if (Greenfoot.isKeyDown("space")) {
            if (Greenfoot.isKeyDown("left")) {
                vSpeed = jumpHeight;
                fall();
                this.setLocation(this.getX() - 5, this.getY() - 5);
                this.state = "jumpHeight";
                moveFoward = false; 
            }
            if (Greenfoot.isKeyDown("right")) {
                vSpeed = jumpHeight;
                fall();
                this.setLocation(this.getX() + 5, this.getY() - 5);
                this.state = "jumpHeight"; 
                moveFoward = true; 
            }
        }
    }

    
    private void fall()
    {
        setLocation(getX(), getY() + vSpeed);
        vSpeed = vSpeed + acceleration;
    }
    public void moveAround()
    {
        if(Greenfoot.isKeyDown("right"))
        {
            move(4);
        }
        if(Greenfoot.isKeyDown("left"))
        {
            move(-4);
        }
        if(Greenfoot.isKeyDown("up")&& isJumping && onGround() == true)
        {
            vSpeed = jumpHeight;
            fall();
        }
    }
    boolean onGround()
    {
        Actor under= getOneObjectAtOffset( 0,getImage().getHeight()/2, Platform.class);
        return under != null;
    }
    public void checkFalling()
    {
        if(onGround() == false)
        {
            fall();
        }
        if(onGround() == true)
        {
            vSpeed = 0;
        }
        if(isJumping && onGround())
        {
            isJumping = false;
            this.state = "landing";
        }
    }

}