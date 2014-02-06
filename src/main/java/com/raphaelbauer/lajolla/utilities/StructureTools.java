package com.raphaelbauer.lajolla.utilities;

///*
// *                  BioJava development code
// *
// * This code may be freely distributed and modified under the
// * terms of the GNU Lesser General Public Licence.  This should
// * be distributed with the code.  If you do not have a copy,
// * see:
// *
// *      http://www.gnu.org/copyleft/lesser.html
// *
// * Copyright for this code is held jointly by the individual
// * authors.  These should be listed in @author doc comments.
// *
// * For more information on the BioJava project and its aims,
// * or to join the biojava-l mailing list, visit the home page
// * at:
// *
// *      http://www.biojava.org/
// * 
// * Created on Jan 4, 2006
// *
// */
//package ra.lajolla.utilities;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import org.biojava.bio.seq.ProteinTools;
//import org.biojava.bio.seq.io.SymbolTokenization;
//import org.biojava.bio.structure.Atom;
//import org.biojava.bio.structure.Chain;
//import org.biojava.bio.structure.Group;
//import org.biojava.bio.structure.GroupIterator;
//import org.biojava.bio.structure.Structure;
//import org.biojava.bio.structure.StructureException;
//import org.biojava.bio.symbol.Alphabet;
//import org.biojava.bio.symbol.IllegalSymbolException;
//import org.biojava.bio.symbol.Symbol;
//
//
///** A class that provides some tool methods.
// * 
// * @author Andreas Prlic
// * @since 1.0
// * @version %I% %G%
// */
//public class StructureTools {
//
//	/** The Atom name of C-alpha atoms.
//	 * 
//	 */
//    public static final String   caAtomName         = "CA" ;
// 
//    /** The names of the Atoms that form the backbone.
//     * 
//     */
//    public static final String[] backboneAtomNames = {"N","CA","C","O","CB"};
//    
//    public static final Character UNKNOWN_GROUP_LABEL = new Character('x');;
//    
//	// there is a file format change in PDB 3.0 and nucleotides are being renamed
//	static private Map<String, Integer> nucleotides30 ;
//	static private Map<String, Integer> nucleotides23 ;
//    
//	
//	// for conversion 3code 1code
//	private static  SymbolTokenization threeLetter ;
//	private static  SymbolTokenization oneLetter ;
//	
//    static {
//		nucleotides30 = new HashMap<String,Integer>();
//		nucleotides30.put("DA",1);
//		nucleotides30.put("DC",1);
//		nucleotides30.put("DG",1);
//		nucleotides30.put("DT",1);
//		nucleotides30.put("DI",1);
//		nucleotides30.put("A",1);
//		nucleotides30.put("G",1);
//		nucleotides30.put("C",1);
//		nucleotides30.put("U",1);
//		nucleotides30.put("I",1);
//
//		//TODO: check if they are always HETATMs, in that case this will not be necessary
//		// the DNA linkers - the +C , +G, +A  +T +U and +I have been replaced with these:
//		nucleotides30.put("TAF",1); // 2'-DEOXY-2'-FLUORO-ARABINO-FURANOSYL THYMINE-5'-PHOSPHATE
//		nucleotides30.put("TC1",1); // 3-(5-PHOSPHO-2-DEOXY-BETA-D-RIBOFURANOSYL)-2-OXO-1,3-DIAZA-PHENOTHIAZINE
//		nucleotides30.put("TFE",1); // 2'-O-[2-(TRIFLUORO)ETHYL] THYMIDINE-5'-MONOPHOSPHATE
//		nucleotides30.put("TFO",1); // [2-(6-AMINO-9H-PURIN-9-YL)-1-METHYLETHOXY]METHYLPHOSPHONIC ACID"
//		nucleotides30.put("TGP",1); // 5'-THIO-2'-DEOXY-GUANOSINE PHOSPHONIC ACID
//		nucleotides30.put("THX",1); // PHOSPHONIC ACID 6-({6-[6-(6-CARBAMOYL-3,6,7,8-TETRAHYDRO-3,6-DIAZA-AS-INDACENE-2-CARBONYL)-3,6,7,8-TETRAHYDRO-3,6-DIAZA-AS-INDOCENE-2-CARBONYL]-3,6,7,8-TETRAHYDRO-3,6-DIAZA-AS-INDACENE-2-CARBONL}-AMINO)-HEXYL ESTER 5-(5-METHYL-2,4-DIOXO-3,4-DIHYDRO-2H-PYRIMIDIN-1-YL)-TETRAHYDRO-FURAN-2-YLMETHYL ESTER
//		nucleotides30.put("TLC",1); // 2-O,3-ETHDIYL-ARABINOFURANOSYL-THYMINE-5'-MONOPHOSPHATE
//		nucleotides30.put("TLN",1); //  [(1R,3R,4R,7S)-7-HYDROXY-3-(THYMIN-1-YL)-2,5-DIOXABICYCLO[2.2.1]HEPT-1-YL]METHYL DIHYDROGEN PHOSPHATE"
//		nucleotides30.put("TP1",1); // 2-(METHYLAMINO)-ETHYLGLYCINE-CARBONYLMETHYLENE-THYMINE
//		nucleotides30.put("TPC",1); // 5'-THIO-2'-DEOXY-CYTOSINE PHOSPHONIC ACID
//		nucleotides30.put("TPN",1); // 2-AMINOETHYLGLYCINE-CARBONYLMETHYLENE-THYMINE
//
//
//
//		// store nucleic acids (C, G, A, T, U, and I), and 
//		// the modified versions of nucleic acids (+C, +G, +A, +T, +U, and +I), and
//		nucleotides23  = new HashMap<String,Integer>();
//		String[] names = {"C","G","A","T","U","I","+C","+G","+A","+T","+U","+I"};
//		for (int i = 0; i < names.length; i++) {
//			String n = names[i];
//			nucleotides23.put(n,1);		
//		}
//		
//		
//		
//		try {
//			Alphabet alpha_prot = ProteinTools.getAlphabet();
//			threeLetter = alpha_prot.getTokenization("name");
//			oneLetter  = alpha_prot.getTokenization("token");
//		} catch (Exception e) {
//			e.printStackTrace() ;
//		}
//
//    }
//    
//    
//    /** Count how many number of Atoms are contained within a Structure object.
//     * 
//     * @param s the structure object
//     * @return the number of Atoms in this Structure
//     */
//    public static int getNrAtoms(Structure s){
//       
//        int nrAtoms = 0;
//        
//        Iterator<Group> iter = new GroupIterator(s);
//        
//        while ( iter.hasNext()){
//            Group g = (Group) iter.next();
//            nrAtoms += g.size();
//        }
//        
//        return nrAtoms;
//    }
//    
//    
//    /** Count how many groups are contained within a structure object.
//     * 
//     * @param s the structure object
//     * @return the number of groups in the structure
//     */
//    public static int getNrGroups(Structure s){
//        int nrGroups = 0;
//        
//        List<Chain> chains = s.getChains(0);
//        Iterator<Chain> iter = chains.iterator();
//        while (iter.hasNext()){
//            Chain c = (Chain) iter.next();
//            nrGroups += c.getAtomLength();
//        }
//        return nrGroups;
//    }
//    
//    
//    /** Returns an array of the requested Atoms from the Structure object. Iterates over all groups
//     * and checks if the requested atoms are in this group, no matter if this is a AminoAcid or Hetatom group.
//     *
//     * 
//     * @param s the structure to get the atoms from 
//     * 
//     * @param atomNames  contains the atom names to be used.
//     * @return an Atom[] array
//     */ 
//    public static Atom[] getAtomArray(Structure s, String[] atomNames){
//        Iterator<Group> iter = new GroupIterator(s);
//        List<Atom> atoms = new ArrayList<Atom>();
//        while ( iter.hasNext()){
//            Group g = (Group) iter.next();
//            
//            // a temp container for the atoms of this group
//            List<Atom> thisGroupAtoms = new ArrayList<Atom>();
//            // flag to check if this group contains all the requested atoms.
//            boolean thisGroupAllAtoms = true;
//            for ( int i = 0 ; i < atomNames.length; i++){
//                String atomName = atomNames[i];
//                try {
//                    Atom a = g.getAtom(atomName);
//                    thisGroupAtoms.add(a);
//                } catch (StructureException e){                	
//                    // this group does not have a required atom, skip it...
//                    thisGroupAllAtoms = false;
//                    break;                   
//                }            
//            }
//            if ( thisGroupAllAtoms){
//                // add the atoms of this group to the array.
//                Iterator<Atom> aIter = thisGroupAtoms.iterator();
//                while(aIter.hasNext()){
//                    Atom a = (Atom) aIter.next();
//                    atoms.add(a);
//                }
//            }
//            
//        }
//        return (Atom[]) atoms.toArray(new Atom[atoms.size()]);
//   
//    } 
//    
//   
//    
//    
//    /** Returns an Atom array of the CA atoms.
//     * @param s the structure object
//     * @return an Atom[] array
//     */
//    public static Atom[] getAtomCAArray(Structure s){
//        String[] atomNames = {caAtomName};
//        return getAtomArray(s,atomNames);
//    }
//    
//    /** Returns an Atom array of the MainChain atoms.
//    
//     * @param s the structure object
//     * @return an Atom[] array
//     */
//    public static Atom[] getBackboneAtomArray(Structure s){
//        String[] atomNames = backboneAtomNames;
//        return getAtomArray(s,atomNames);
//    }
//
//    
//    /** convert three character amino acid codes into single character
//	 *  e.g. convert CYS to C 
//	 *  @return a character
//	 *  @param code3 a three character amino acid representation String
//	 *  @throws IllegalSymbolException
//	 */
//
//	public static Character convert_3code_1code(String code3) 
//	throws IllegalSymbolException
//	{
//		Symbol sym   =  threeLetter.parseToken(code3) ;
//		String code1 =  oneLetter.tokenizeSymbol(sym);
//
//		return new Character(code1.charAt(0)) ;
//
//	}
//    
//    /** convert a three letter code into single character.
//	 * catches for unusual characters
//	 * 
//	 * @param groupCode3 three letter representation
//	 * @return null if group is a nucleotide code
//	 */
//	public static Character get1LetterCode(String groupCode3){
//
//		Character aminoCode1 = null;
//		try {
//			// is it a standard amino acid ?
//			aminoCode1 = convert_3code_1code(groupCode3);
//		} catch (IllegalSymbolException e){
//			// hm groupCode3 is not standard
//			// perhaps it is an nucleotide?
//			if ( isNucleotide(groupCode3) ) {
//				//System.out.println("nucleotide, aminoCode1:"+aminoCode1);
//				//aminoCode1= null;
//			} else {
//				// does not seem to be so let's assume it is 
//				//  nonstandard aminoacid and label it "X"
//				//System.out.println("unknown group name "+groupCode3 );
//				aminoCode1 = UNKNOWN_GROUP_LABEL; 
//			}
//		}
//
//		return aminoCode1;
//
//	}
//    
//    
//    /* Test if the threelettercode of an ATOM entry corresponds to a
//	 * nucleotide or to an aminoacid.
//	 * @param a 3-character code for a group.
//	 * 
//	 */
//	public static boolean isNucleotide(String groupCode3){
//
//		String code = groupCode3.trim();
//		if ( nucleotides30.containsKey(code)){    	
//			return true;    		
//		}
//
//		if ( nucleotides23.containsKey(code)){
//			return true;
//		}
//
//		return false ;
//	}
//    
//}
