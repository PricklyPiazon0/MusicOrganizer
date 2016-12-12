import java.util.ArrayList;
import java.util.Random;

/**
 * A class to hold details of audio tracks.
 * Individual tracks may be played. Modified to play one random track from already added tracks
 * and to randomize the play order of all added tracks
 * 
 * @author David J. Barnes and Michael Kölling
 * @author Dana Sabatino
 * @version 2016.12.11
 */
public class MusicOrganizer
{
    // An ArrayList for storing music tracks.
    private ArrayList<Track> tracks;
    // A player for the music tracks.
    private MusicPlayer player;
    // A reader that can read music files and load them as tracks.
    private TrackReader reader;
    // a random number generator for randomizing track playlists
    private Random rand = new Random();

    /**
     * Create a MusicOrganizer
     */
    public MusicOrganizer()
    {
        tracks = new ArrayList<Track>();
        player = new MusicPlayer();
        reader = new TrackReader();
        readLibrary("audio");
        System.out.println("Music library loaded. " + getNumberOfTracks() + " tracks.");
        System.out.println();
    }
    
    /**
     * Add a track file to the collection.
     * @param filename The file name of the track to be added.
     */
    public void addFile(String filename)
    {
        tracks.add(new Track(filename));
    }
    
    /**
     * Add a track to the collection.
     * @param track The track to be added.
     */
    public void addTrack(Track track)
    {
        tracks.add(track);
    }
    
    /**
     * Play a track in the collection.
     * @param index The index of the track to be played.
     */
    public void playTrack(int index)
    {
        if(indexValid(index)) {
            Track track = tracks.get(index);
            player.startPlaying(track.getFilename());
            System.out.println("Now playing: " + track.getArtist() + " - " + track.getTitle());
        }
    }
    
    /**
     * Return the number of tracks in the collection.
     * @return The number of tracks in the collection.
     */
    public int getNumberOfTracks()
    {
        return tracks.size();
    }
    
    /**
     * List a track from the collection.
     * @param index The index of the track to be listed.
     */
    public void listTrack(int index)
    {
        System.out.print("Track " + index + ": ");
        Track track = tracks.get(index);
        System.out.println(track.getDetails());
    }
    
    /**
     * Show a list of all the tracks in the collection.
     */
    public void listAllTracks()
    {
        System.out.println("Track listing: ");

        for(Track track : tracks) {
            System.out.println(track.getDetails());
        }
        System.out.println();
    }
    
    /**
     * List all tracks by the given artist.
     * @param artist The artist's name.
     */
    public void listByArtist(String artist)
    {
        for(Track track : tracks) {
            if(track.getArtist().contains(artist)) {
                System.out.println(track.getDetails());
            }
        }
    }
    
    /**
     * Remove a track from the collection.
     * @param index The index of the track to be removed.
     */
    public void removeTrack(int index)
    {
        if(indexValid(index)) {
            tracks.remove(index);
        }
    }
    
    /**
     * Play the first track in the collection, if there is one.
     */
    public void playFirst()
    {
        if(tracks.size() > 0) {
            player.startPlaying(tracks.get(0).getFilename());
        }
    }
    
    /**
     * Stop the player.
     */
    public void stopPlaying()
    {
        player.stop();
    }

    /**
     * Determine whether the given index is valid for the collection.
     * Print an error message if it is not.
     * @param index The index to be checked.
     * @return true if the index is valid, false otherwise.
     */
    private boolean indexValid(int index)
    {
        // The return value.
        // Set according to whether the index is valid or not.
        boolean valid;
        
        if(index < 0) {
            System.out.println("Index cannot be negative: " + index);
            valid = false;
        }
        else if(index >= tracks.size()) {
            System.out.println("Index is too large: " + index);
            valid = false;
        }
        else {
            valid = true;
        }
        return valid;
    }
    
    private void readLibrary(String folderName)
    {
        ArrayList<Track> tempTracks = reader.readTracks(folderName, ".mp3");

        // Put all thetracks into the organizer.
        for(Track track : tempTracks) {
            addTrack(track);
        }
    }
    
    /**
     * plays a random number track from the tracks arrayList of music tracks
     */
    public void playRandomTrack()
    {
        player.startPlaying(tracks.get(rand.nextInt(tracks.size())).getFilename());
    }
    
    /**
     * creates a random playlist of tracks for playing each track only once, but
     * in a random order
     */
    public void randomizePlayList()
    {
        int[] randomList = new int[tracks.size()];
        int randNum = -1;
        boolean isNotUnique = false;
        for (int index = 0; index <tracks.size(); index++)
        {
            do
            {
                randNum = rand.nextInt(tracks.size());
                if(!(isNotUnique = isInArray(randomList, randNum, index)))
                {
                    randomList[index] = randNum;
                }
            }while(isNotUnique);
            player.startPlaying(tracks.get(index).getFilename());
        }
       
    }
    
       /**
       * method to return whether a passed integer is in an array of integers
       * 
       * @param randomList is a integer array of random number
       * @param num is a number passed in to compare with already entered values in randomList
       * @param index is the current element of randomList array that is being filled all entries
       *        in array randomList that have an index < index will be compared
       * @return returns true if num is an element of randomList, false otherwise
       */
    private boolean isInArray(int[] randomList, int num, int index)
    {
        for(int searchVariable = 0; searchVariable < index; searchVariable++)
        {
            if(randomList[searchVariable] == num)
            {
                return true;
            }
        }
        return false;
    }
}