package nl.tue;

/**
 * The interface Moving.
 */
public interface Moving {
    /**
     * Start moving.
     */
    void startMoving();

    /**
     * Stop moving.
     */
    void stopMoving();

    /**
     * Updates.
     */
    void updates();

    /**
     * Gets x pos.
     *
     * @return the x pos
     */
    int getXPos();

    /**
     * Gets y pos.
     *
     * @return the y pos
     */
    int getYPos();
}
