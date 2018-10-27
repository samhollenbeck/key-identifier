import java.util.ArrayList;

public class Key{

private String[] notes;
private int[] correctIntervals = new int[]{0,2,4,5,7,9,11};

  public Key(){
    notes = new String[12];
  }

  public Key(String[] notes){
    this.notes = notes;
  }

  public int getInterval(String start, String end){
    int span = 0;
    for (int i = 0; i < notes.length; i++) {
      for (int j = 0; j < notes.length; j++){
        if (notes[i] == start && notes[j] == end)
        {
          span = j - i;
          break;
        }
      }
    }
    if(span < 0)
      span = 12 + span;

    return span;
  }

  public ArrayList<String> possibleKeys(String[] chords){

    ArrayList<String> possibleKeys = new ArrayList<String>();

    int[] intervals = convertToIntervals(chords);

    for(int i = 0; i < 11; i++){
      if(isKey(intervals))
        possibleKeys.add(whichKeyIsThis(intervals,chords));
      intervals = increaseByOne(intervals);
    }
    return possibleKeys;
  }

public int[] convertToIntervals(String[] chords){
  String root = chords[0];
  int[] output = new int[chords.length];
  output[0] = 0;

  for(int i = 1; i < chords.length; i++)
    output[i] = getInterval(root,chords[i]);

  return output;
}

  public int[] increaseByOne(int[] intervals){
    int[] output = intervals;

    for(int i = 0; i < intervals.length; i++)
    {
      if(output[i] == 11)
        output[i] = 0;
      else
        output[i] = output[i] + 1;
    }
    return output;
  }

//IDENTIFY IF AN ARRAY OF INTERVALS ALL CONTAIN CORRECT INTERVALS FOR A MAJOR KEY
  public boolean isKey(int[] intervals){
    boolean value = true;

    for(int z:intervals)
      System.out.println(z);
    System.out.println("");

    for(int x: intervals){
      for(int y: correctIntervals){
        if(x != y)
          value = false;
      }
      if (value == false)
          return false;
    }

    return true;

  }

  public String whichKeyIsThis(int[] interval, String[] chords)
  {
    int j = 0;
    for (int k = 0; k < notes.length; k++) {
      if(notes[k] == chords[0])
        j = k;
    }

    int span = interval[0];
    int i = j - span;

    return notes[i];
  }

  public static void main (String[] args){

    String[] chords = new String[]{"F","C","G","A"};

    Key test = new Key(new String[] {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"});
    //System.out.println(test.getInterval("D","G"));
    System.out.println(test.possibleKeys(new String[]{"A","C#","F#","B"}));
  }
}
