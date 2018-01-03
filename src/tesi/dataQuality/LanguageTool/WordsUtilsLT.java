package tesi.dataQuality.LanguageTool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeSet;

import tesi.dataQuality.model.Parola;

/**
 * Classe per la gestione delle eccezioni
 * per il tool LanguageTool
 * @author PC-Simone
 *
 */
public class WordsUtilsLT {
	
	/** The single instance of this class. */
	private static final WordsUtilsLT _theInstance = new WordsUtilsLT();
	private static TreeSet<String> toIgnorePermanently = new TreeSet<String>(); 
	private static TreeSet<String> toIgnoreTemporarily = new TreeSet<String>();
	private static TreeSet<String> toIgnoreWords = new TreeSet<String>();
	
	public static TreeSet<String> getToIgnoreWords(){
		toIgnorePermanently.addAll(load());
		toIgnoreWords.clear();
		toIgnoreWords.addAll(toIgnorePermanently);
		toIgnoreWords.addAll(toIgnoreTemporarily);
		save();
		return toIgnoreWords;
	}
	
	public static TreeSet<Parola> getToIgnoreShow(){
		TreeSet<Parola> words = new TreeSet<Parola>();
		for(String str : toIgnoreTemporarily) {
			words.add(new Parola(str));
		}
		toIgnorePermanently.addAll(load());
		for(String str : toIgnorePermanently) {
			words.add(new Parola(str));
		}
		save();
		return words;
	}
	
	public static WordsUtilsLT getInstance() {
		return _theInstance;
	}
	
	public static void deleteWordPermanently(String word){
		WordsUtilsLT.toIgnorePermanently.remove(word);
		save();
	}
	
	public static void addWordPermanently(String word){
		WordsUtilsLT.toIgnorePermanently.add(word);
		save();
	}
	
	public static void deleteAllPermanently(){
		WordsUtilsLT.toIgnorePermanently.clear();
		save();
	}

	public static void deleteAllTemporarily() {
		WordsUtilsLT.toIgnoreTemporarily.clear();
	}
	
	public static void deleteWordTemporarily(String word) {
		WordsUtilsLT.toIgnoreTemporarily.remove(word);
	}

	public static void addWordTemporarily(String word) {
		WordsUtilsLT.toIgnoreTemporarily.add(word);
	}
	
	public static boolean checkToIgnore(String str) {
		toIgnorePermanently.addAll(load());
		if(WordsUtilsLT.toIgnorePermanently.contains(str) || WordsUtilsLT.toIgnoreTemporarily.contains(str)) {
			return true;
		}
		else
			return false;
	}
	
	/**
	 *  Sole constructor, private because this is a Singleton class.
	 */
	private WordsUtilsLT() {
	}

	@SuppressWarnings("unchecked")
	private static TreeSet<String> load(){
		File f = new File("support/toIgnorePermanentlyLT.dat");
		
		if(f.exists() && !f.isDirectory()) {
			try {
				FileInputStream inFile = new FileInputStream("support/toIgnorePermanentlyLT.dat");
				ObjectInputStream inStream = new ObjectInputStream(inFile);
				TreeSet<String> words = (TreeSet<String>) inStream.readObject();
				inStream.close();
				return words;
				
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("Error");
			}
			
			
		}
		
		
		TreeSet<String> toIgn= new TreeSet<String>();
		ignore(toIgn);
		
		return toIgn;
				
	}

	private static void ignore(TreeSet<String> toIgn) {
		toIgn.add("decitex");
		toIgn.add("caoliniche");
		toIgn.add("sillimanite");
		toIgn.add("chamotte");
		toIgn.add("dinas");
		toIgn.add("asfaltiti");
		toIgn.add("pellet");
		toIgn.add("boniti");
		toIgn.add("seppiole");
		toIgn.add("pellets");
		toIgn.add("Müsli");
		toIgn.add("oleostearina");
		toIgn.add("linters");
		toIgn.add("Degras");
		toIgn.add("Fontal");
		toIgn.add("Leguminoso");
		toIgn.add("isoglucosio");
		toIgn.add("maltodestrina");
		toIgn.add("knäckebrot");
		toIgn.add("chocolate");
		toIgn.add("milk");
		toIgn.add("crumb");
		toIgn.add("Ketchup");
		toIgn.add("Premiscele");
		toIgn.add("genièvre");
		toIgn.add("alcolometrico");
		toIgn.add("vol");
		toIgn.add("Pettinacce");
		toIgn.add("ramiè");
		toIgn.add("tufted");
		toIgn.add("Gobelins");
		toIgn.add("Aubusson");
		toIgn.add("Beauvais");
		toIgn.add("copriletti");
		toIgn.add("Abaca");
		toIgn.add("tulli");
		toIgn.add("bobinots");
		toIgn.add("groppetti");
		toIgn.add("Waferboard");
		toIgn.add("kraftliner");
		toIgn.add("fluting");
		toIgn.add("Testliner");
		toIgn.add("kraft");
		toIgn.add("avvivaggio");
		toIgn.add("ossialogenuri");
		toIgn.add("clorosolforico");
		toIgn.add("Pentaossido");
		toIgn.add("difosforo");
		toIgn.add("polifosforici");
		toIgn.add("Floruro");
		toIgn.add("fluroralluminati");
		toIgn.add("ossibromuri");
		toIgn.add("ipobromiti");
		toIgn.add("solfossilati");
		toIgn.add("perbromati");
		toIgn.add("Perossolfati");
		toIgn.add("persolfati");
		toIgn.add("Fosfinati");
		toIgn.add("fosfonati");
		toIgn.add("disodio");
		toIgn.add("tripolifosfato");
		toIgn.add("Idrogenocarbonato");
		toIgn.add("dicromati");
		toIgn.add("perossocromati");
		toIgn.add("ossometallici");
		toIgn.add("perossometallici");
		toIgn.add("perossoacidi");
		toIgn.add("ossicianuri");
		toIgn.add("perossoborati");
		toIgn.add("ferrofosfori");
		toIgn.add("siliciuri");
		toIgn.add("Cicloparafficini");
		toIgn.add("ciclooleifinici");
		toIgn.add("cicloterpenici");
		toIgn.add("Clorometano");
		toIgn.add("cloroetano");
		toIgn.add("Diclorometano");
		toIgn.add("cloroetilene");
		toIgn.add("tetracloroetilene");
		toIgn.add("percloroetilene");
		toIgn.add("cicloparaffinici");
		toIgn.add("cicloolefinici");
		toIgn.add("Ottanolo");
		toIgn.add("monoalcoli");
		toIgn.add("etandiolo");
		toIgn.add("glucitolo");
		toIgn.add("polialcoli");
		toIgn.add("Monofenoli");
		toIgn.add("tricloroacetici");
		toIgn.add("fenilacetico");
		toIgn.add("Ortoftalati");
		toIgn.add("azelaico");
		toIgn.add("diottile");
		toIgn.add("ortoftalico");
		toIgn.add("dibutile");
		toIgn.add("policarbossilici");
		toIgn.add("perossiacidi");
		toIgn.add("monoammine");
		toIgn.add("etilendiammina");
		toIgn.add("Monoammine");
		toIgn.add("cicloparaffiniche");
		toIgn.add("cicloolefiniche");
		toIgn.add("cicloterpeniche");
		toIgn.add("Monoetanolammina");
		toIgn.add("Dietanolammina");
		toIgn.add("Trietanolammina");
		toIgn.add("Ammino");
		toIgn.add("Ureine");
		toIgn.add("Immidi");
		toIgn.add("Immine");
		toIgn.add("azo");
		toIgn.add("azossi");
		toIgn.add("tiocomposti");
		toIgn.add("isochinolinico");
		toIgn.add("pirazolico");
		toIgn.add("piperazinico");
		toIgn.add("performaldeide");
		toIgn.add("triazinico");
		toIgn.add("Butanone");
		toIgn.add("Butanale");
		toIgn.add("metiletilchetone");
		toIgn.add("benzotiazolo");
		toIgn.add("metilcicloesanoni");
		toIgn.add("butirraldeide");
		toIgn.add("metiliononi");
		toIgn.add("lattofosfati");
		toIgn.add("Tallol");
		toIgn.add("Ossirano");
		toIgn.add("emiacetali");
		toIgn.add("Idrogenoortofosfato");
		toIgn.add("Metilossirano");
		toIgn.add("diammonio");
		toIgn.add("epossi");
		toIgn.add("diammonico");
		toIgn.add("Diidrogenoortofosfato");
		toIgn.add("monoammonico");
		toIgn.add("Policloruro");
		toIgn.add("Poliacetali");
		toIgn.add("Polietilenglicoli");
		toIgn.add("polieteralcoli");
		toIgn.add("alchidiche");
		toIgn.add("cumaronindeniche");
		toIgn.add("politerpeni");
		toIgn.add("polisolfoni");
		toIgn.add("Aldrin");
		toIgn.add("aldicarb");
		toIgn.add("carbofuran");
		toIgn.add("metomil");
		toIgn.add("simazina");
		toIgn.add("organofosforati");
		toIgn.add("piretroidi");
		toIgn.add("alacloro");
		toIgn.add("butacloro");
		toIgn.add("profamio");
		toIgn.add("barbano");
		toIgn.add("dinitroaniline");
		toIgn.add("trifluralina");
		toIgn.add("pendimetalina");
		toIgn.add("sulfonilurati");
		toIgn.add("dalapon");
		toIgn.add("dicamba");
		toIgn.add("ziram");
		toIgn.add("ditiocarbammati");
		toIgn.add("benomil");
		toIgn.add("diazoli");
		toIgn.add("benzimidazoli");
		toIgn.add("triadimefon");
		toIgn.add("triforine");
		toIgn.add("aldrina");
		toIgn.add("binapacril");
		toIgn.add("clorobenzilato");
		toIgn.add("clordimeforme");
		toIgn.add("clordano");
		toIgn.add("captafolo");
		toIgn.add("tossafene");
		toIgn.add("camfecloro");
		toIgn.add("clofenotano");
		toIgn.add("glicerinose");
		toIgn.add("Shampooings");
		toIgn.add("polietilenglicoli");
		toIgn.add("Alchilbenzeni");
		toIgn.add("alchilnaftaleni");
		toIgn.add("etanolammine");
		toIgn.add("ossirano");
		toIgn.add("bifenili");
		toIgn.add("polibromurati");
		toIgn.add("policlorurati");
		toIgn.add("trifenili");
		toIgn.add("ovoalbumina");
		toIgn.add("aramidi");
		toIgn.add("Monofilamenti");
		toIgn.add("fosfoamminolipidi");
		toIgn.add("ureine");
		toIgn.add("fenotiazinici");
		toIgn.add("Solfonammidi");
		toIgn.add("Eterosidi");
		toIgn.add("protovitamine");
		toIgn.add("MPa");
		toIgn.add("butirrale");
		toIgn.add("antiaderenziali");
		toIgn.add("policloruro");
		toIgn.add("camescopes");
		toIgn.add("flotté");
		toIgn.add("rovings");
		toIgn.add("mats");
		toIgn.add("spaltplatten");
		toIgn.add("grès");
		toIgn.add("copriferro");
		toIgn.add("klinkers");
		toIgn.add("semicolloidale");
		toIgn.add("lingottati");
		toIgn.add("bordione");
		toIgn.add("elicoidalmente");
		toIgn.add("sinter");
		toIgn.add("colombio");
		toIgn.add("cermet");
		toIgn.add("aprilettere");
		toIgn.add("Tagliatubi");
		toIgn.add("tagliabulloni");
		toIgn.add("foratoi");
		toIgn.add("ocermet");
		toIgn.add("carmet");
		toIgn.add("coprituraccioli");
		toIgn.add("passalacci");
		toIgn.add("iperfrequenza");
		toIgn.add("diac");
		toIgn.add("triac");
		toIgn.add("multichip");
		toIgn.add("cache");
		toIgn.add("controller");
		toIgn.add("microassiemaggi");
		toIgn.add("hub");
		toIgn.add("router");
		toIgn.add("gateway");
		toIgn.add("routing");
		toIgn.add("Teleproiettori");
		toIgn.add("Videotuner");
		toIgn.add("wafers");
		toIgn.add("diffrattografi");
		toIgn.add("Manostati");
		toIgn.add("pendolette");
		toIgn.add("orodatari");
		toIgn.add("microformati");
		toIgn.add("cinefotomicrografia");
		toIgn.add("micromacchine");
		toIgn.add("kVA");
		toIgn.add("kV");
		toIgn.add("Hz");
		toIgn.add("kVar");
		toIgn.add("elicocentrifughe");
		toIgn.add("criostatiche");
		toIgn.add("avanfocolari");
		toIgn.add("cavaliers");
		toIgn.add("Autocarrelli");
		toIgn.add("diserbatrici");
		toIgn.add("autocarrelli");
		toIgn.add("Falciatrinciacaricatrici");
		toIgn.add("scartocciatrici");
		toIgn.add("nebulizzatrici");
		toIgn.add("autoscaricanti");
		toIgn.add("autocaricanti");
		toIgn.add("centinatrici");
		toIgn.add("sgretolatrici");
		toIgn.add("rifollatura");
		toIgn.add("lingotterie");
		toIgn.add("angledozer");
		toIgn.add("aspare");
		toIgn.add("copripavimenti");
		toIgn.add("jacquard");
		toIgn.add("termoformatrici");
		toIgn.add("cart");
		toIgn.add("cm³");
		toIgn.add("automarkets");
		toIgn.add("locotrattori");
		toIgn.add("kw");
		toIgn.add("orchestrion");
		toIgn.add("unifocali");
		toIgn.add("stilografi");
		toIgn.add("baudruche");
		toIgn.add("linolenico");
		toIgn.add("sciarpette");
		toIgn.add("eterociclici");
		toIgn.add("maleico");
		toIgn.add("adipico");
		toIgn.add("laurico");
		toIgn.add("solfonitrici");
		toIgn.add("palmitico");
		toIgn.add("Acrilonitrile");
		toIgn.add("albuminati");
		toIgn.add("anionici");
		toIgn.add("fresatrici");
		toIgn.add("cetilico");
		toIgn.add("laurilico");
		toIgn.add("alesatrici");
		toIgn.add("cationici");
		toIgn.add("silico");
		toIgn.add("biassiale");
		toIgn.add("scaldapiatti");
		toIgn.add("butanoli");
		toIgn.add("epossidi");
		toIgn.add("monofilamenti");
		toIgn.add("semiprodotti");
		toIgn.add("kW");
		toIgn.add("semipreziose");
		toIgn.add("portapezzi");
		toIgn.add("azoturi");
		toIgn.add("spanditori");
		toIgn.add("cementisti");
		toIgn.add("ballast");
		toIgn.add("car");
		toIgn.add("carbammati");
		toIgn.add("maleica");
		toIgn.add("Anodizzazione");
		toIgn.add("Antisieri");
		toIgn.add("elettrodiagnosi");
		toIgn.add("termocopia");
		toIgn.add("meccanoterapia");
		toIgn.add("elettrotermici");
		toIgn.add("scaldatori");
		toIgn.add("cordless");
		toIgn.add("cianite");
		toIgn.add("mullite");
		toIgn.add("cappellinai");
		toIgn.add("montasio");
		toIgn.add("dumper");
		toIgn.add("Autogru");
		toIgn.add("cg");
		toIgn.add("Bismuti");
		toIgn.add("ossiioduri");
		toIgn.add("Butene");
		toIgn.add("butilene");
		toIgn.add("calzemaglie");
		toIgn.add("Calzemaglie");
		toIgn.add("semichimica");
		toIgn.add("g/m²");
		toIgn.add("fm");
		toIgn.add("testliner");
		toIgn.add("ossicloruri");
		toIgn.add("oleoidrauliche");
		toIgn.add("Catgut");
		toIgn.add("xilolo");
		toIgn.add("specolare");
		toIgn.add("Ballast");
		toIgn.add("catgut");
		toIgn.add("xileni");
		toIgn.add("celtio");
		toIgn.add("m²");
		toIgn.add("Caseinati");
		toIgn.add("cialdine");
		toIgn.add("ammino");
		toIgn.add("Portland");
		toIgn.add("diazo");
		toIgn.add("eteroatomi");
		toIgn.add("lattoni");
		toIgn.add("imidazolico");
		toIgn.add("Cicloesanone");
		toIgn.add("Compattatori");
		toIgn.add("piridinico");
		toIgn.add("lattami");
		toIgn.add("chinolinico");
		toIgn.add("triclorometano");
		toIgn.add("imidazolico");
		toIgn.add("uracili");
		toIgn.add("sisal");
		toIgn.add("Copolimeri");
		toIgn.add("acrilonitrile");
		toIgn.add("system");
		toIgn.add("split");
		toIgn.add("Cromatografi");
		toIgn.add("Cumene");
		toIgn.add("fototransistori");
		toIgn.add("mandrini");
		toIgn.add("Emmenthal");
		toIgn.add("fitormoni");
		toIgn.add("triazine");
		toIgn.add("Etanale");
		toIgn.add("Etilbenzene");
		toIgn.add("sago");
		toIgn.add("Fluting");
		toIgn.add("fontal");
		toIgn.add("segalato");
		toIgn.add("elettropneumatiche");
		toIgn.add("soffiaggio");
		toIgn.add("fluorosilicati");
		toIgn.add("idrogenoortofosfato");
		toIgn.add("Ferromanganese");
		toIgn.add("trifosfato");
		toIgn.add("boruri");
		toIgn.add("diazine");
		toIgn.add("morfoline");
		toIgn.add("Ferrosilicio");
		toIgn.add("tini");
		toIgn.add("negatoscopi");
		toIgn.add("tri");
		toIgn.add("Glicole");
		toIgn.add("etilenico");
		toIgn.add("metalloplastiche");
		toIgn.add("dicalcico");
		toIgn.add("mezzoguanti");
		toIgn.add("asciugatrici");
		toIgn.add("listellata");
		toIgn.add("polimetacrilato");
		toIgn.add("nervate");
		toIgn.add("personal");
		toIgn.add("parchetti");
		toIgn.add("flessografici");
		toIgn.add("gr");
		toIgn.add("brocciare");
		toIgn.add("computer");
		toIgn.add("Linters");
		toIgn.add("eliografici");
		toIgn.add("antiraggi");
		toIgn.add("silos");
		toIgn.add("klystron");
		toIgn.add("guipure");
		toIgn.add("Malonilurea");
		toIgn.add("Maltodestrina");
		toIgn.add("Mandrini");
		toIgn.add("Melamina");
		toIgn.add("volframati");
		toIgn.add("Manganiti");
		toIgn.add("Metalline");
		toIgn.add("tungstati");
		toIgn.add("cuprifere");
		toIgn.add("portamine");
		toIgn.add("Microvetture");
		toIgn.add("Metanale");
		toIgn.add("dibromopropil");
		toIgn.add("calamaretti");
		toIgn.add("Monoalcoli");
		toIgn.add("trimetilammina");
		toIgn.add("Motozappatrici");
		toIgn.add("portacontainer");
		toIgn.add("portarinfuse");
		toIgn.add("ro");
		toIgn.add("trombossani");
		toIgn.add("leucotrieni");
		toIgn.add("idrossicloruri");
		toIgn.add("Ossicloruri");
		toIgn.add("ottilico");
		toIgn.add("denim");
		toIgn.add("Paraformaldeide");
		toIgn.add("idroestrattori");
		toIgn.add("side");
		toIgn.add("coke");
		toIgn.add("cm²");
		toIgn.add("Polimetacrilato");
		toIgn.add("semichimiche");
		toIgn.add("aerometri");
		toIgn.add("Pellet");
		toIgn.add("gres");
		toIgn.add("Roots");
		toIgn.add("Portamine");
		toIgn.add("alacce");
		toIgn.add("Portapezzi");
		toIgn.add("spratti");
		toIgn.add("estintrici");
		toIgn.add("shampooings");
		toIgn.add("premiscele");
		toIgn.add("Pressapaglia");
		toIgn.add("Propene");
		toIgn.add("flaps");
		toIgn.add("Provitamine");
		toIgn.add("melamminiche");
		toIgn.add("Resinoidi");
		toIgn.add("tiofenici");
		toIgn.add("scopine");
		toIgn.add("brindilli");
		toIgn.add("Semiprodotti");
		toIgn.add("calorifugo");
		toIgn.add("asolatura");
		toIgn.add("trisolfuro");
		toIgn.add("ditioniti");
		toIgn.add("butile");
		toIgn.add("capospalla");
		toIgn.add("Spanditori");
		toIgn.add("ananasso");
		toIgn.add("pomelo");
		toIgn.add("agugliati");
		toIgn.add("floccati");
		toIgn.add("antiappannanti");
		toIgn.add("Denim");
		toIgn.add("essiccapasta");
		toIgn.add("diarsenico");
		toIgn.add("sal");
		toIgn.add("Tulli");
		toIgn.add("tagliasiepi");
		toIgn.add("maschiare");
		toIgn.add("Trifosfato");
		toIgn.add("Videomonitor");
		toIgn.add("Madera");
		
	}

	private static void save(){
		try {
			FileOutputStream outFile = new FileOutputStream("support/toIgnorePermanentlyLT.dat");
			ObjectOutputStream outStream = new ObjectOutputStream(outFile);
			outStream.writeObject(WordsUtilsLT.toIgnorePermanently);
			outStream.close();
		} catch (IOException e) {
			System.out.println("Error");
		}
		
	}

}
