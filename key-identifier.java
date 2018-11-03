import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Key{

  private String[] notes;
  private String root;
  private int[] correctIntervals = new int[]{0,2,4,5,7,9,11};
  private HashMap<String, int[]> chordIntervals = new HashMap<String, int[]>();

  public Key(){
    notes = new String[12];
  }

  public Key(String[] notes){
    this.notes = notes;
  }

  public void setchordIntervals(){
    chordIntervals.put("M",    new int[]{0, 4, 7});
    chordIntervals.put("m",    new int[]{0, 3, 7});
    chordIntervals.put("sus2", new int[]{0, 2, 7});
    chordIntervals.put("sus4", new int[]{0, 5, 7});
    chordIntervals.put("maj7", new int[]{11});
    chordIntervals.put("7",    new int[]{10});
    chordIntervals.put("dim",  new int[]{0,3,6});

  }
  public void printArray(int[] arr, String title)
  {  System.out.println(title);
    for(int item : arr)
        System.out.println(item);
  }

  public ArrayList<ArrayList<String>> arrangeChords(String[] input){
    ArrayList<ArrayList<String>> notesAndChords = new ArrayList<ArrayList<String>>();

    for(String chord: input)
    {
      ArrayList<String> item = new ArrayList<String>();
      for(String key : chordIntervals.keySet())
      {
        if(chord.indexOf(key) > -1)
        {
          chord = chord.replace(key,"");
          item.add(key);
        }
      }
        item.add(0, chord);
        notesAndChords.add(item);
    }
    return notesAndChords;
  }


  public int[] convertChordArraysToIntervals(ArrayList<ArrayList<String>> notesAndChords){
    root = notesAndChords.get(0).get(0);
    ArrayList<Integer> notes = new ArrayList<Integer>();
    for(ArrayList<String> x: notesAndChords)
    {
      notes.add(getInterval(root, x.get(0)));
      for(int i = 1; i < x.size(); i++){
        notes.addAll(shiftIntervals(root, x.get(0), chordIntervals.get(x.get(i))));}
    }
    int[] output = integerToInt(notes);
    return output;
  }

  public int[] integerToInt(ArrayList<Integer> integers) {
    int[] ints = new int[integers.size()];
    int i = 0;
    for (Integer n : integers)
        ints[i++] = n;
    return ints;
  }
  public ArrayList<Integer> intToInteger(int[] ints) {
    ArrayList<Integer> integers = new ArrayList<Integer>();
    int i = 0;
    for (int n : ints)
      integers.add(Integer.valueOf(n));
    return integers;
  }

  public ArrayList<Integer> shiftIntervals(String root, String chordroot, int[] intervals){
    int difference = getInterval(root, chordroot);
    int[] output = increaseByValue(intervals, difference);
    return intToInteger(output);
  }

  public int getInterval(String start, String end){
    int span = 0;
    for (int i = 0; i < notes.length; i++) {
      for (int j = 0; j < notes.length; j++){
        if (notes[i].equals(start) && notes[j].equals(end))
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

    int[] intervals = convertChordArraysToIntervals(arrangeChords(chords));

    for(int i = 0; i < 12; i++){
      if(isKey(intervals))
        possibleKeys.add(whichKeyIsThis(intervals, this.root));
      intervals = increaseByOne(intervals);
    }
    System.out.println("");
    System.out.println("This sequence could be in the keys of...");
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

  public int[] increaseByValue(int[] intervals, int value){
    int[] output = intervals;
    for(int i = 0; i < intervals.length; i++)
    {
      if(output[i] + value > 11)
        output[i] = output[i] + value - 12;
      else
        output[i] = output[i] + value;
    }
      setchordIntervals();
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

    for(int x: intervals){
      boolean value = false;

      for(int y: correctIntervals){
        if(x == y){
          value = true;
          break;
        }
      }
      if(!value)
       return false;
    }
    return true;
  }

  public String whichKeyIsThis(int[] interval, String root)
  {
    int j = 0;
    for (int k = 0; k < notes.length; k++) {
      if(notes[k].equals(root))
        j = k;

    }

    int span = interval[0];
    int i = j - span;

    if(i < 0)
      i = 12 + i;
    return notes[i];
  }

  public static void main (String[] args){

    Key test = new Key(new String[] {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"});

    test.setchordIntervals();
    //System.out.println(test.possibleKeys(new String[]{"CM","GM", "Am", "FM"}));
    Scanner keyboard = new Scanner(System.in);
    System.out.println("Enter your chords and/or notes:");
    ArrayList<String> input = new ArrayList<String>();

    while(keyboard.hasNextLine()){
        input.add(keyboard.nextLine());
    }
    System.out.println(test.possibleKeys(input.toArray(new String[0])));
  }
}
