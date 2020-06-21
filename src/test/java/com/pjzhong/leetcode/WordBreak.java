package com.pjzhong.leetcode;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Given a non-empty string s and a dictionary wordDict containing a list of non-empty words,
 * determine if s can be segmented into a space-separated sequence of one or more dictionary words.
 *
 * Note:
 *
 * The same word in the dictionary may be reused multiple times in the segmentation. You may assume
 * the dictionary does not contain duplicate words. Example 1:
 *
 * <p>Input: s = "leetcode", wordDict = ["leet", "code"]</p>
 * <p>Output: true</p>
 * <p>Explanation: Return true because "leetcode" can be segmented as "leet code".</p>
 *
 * @author ZJP
 * @link https://leetcode.com/problems/word-break/
 * @since 2020年06月21日 22:00:19
 **/
public class WordBreak {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Test
  public void test() {

    String[] worlds = {
        "aaaaaaa",
        "ccbb",
        "pjzhongzh",
        "ccaccc",
        "applepenapple",
        "catsandog",
        "leetcode",
        "pjzhong",
        "pjzhong",
        "fohhemkkaecojceoaejkkoedkofhmohkcjmkggcmnami",
        "aagdggdhdlgksklhghgdhkgakahjdhjsjfkkggljaksadajaskjlljlglfahjajlaasaksjsfasddaklggjssglglhfhjjglgjllfjsfslalhlskggfjfsghgfffjkgkhjjdhhjfdfgdhsdhfggfgadhsgglaahldsfjllahlklfdsdsglgfhjkdsllhsfdkljldkjlghkkdakadfflkjajshssdkdkjafkjadglkslkaksgfdlfglfjgkskdkaaaagdksasgldkflfsshlaffkfflkgkakashhdssjgsahfkagllhfsaffsfgsjlfkjjafssfggdhkaksdadfaaafldafffaffgdsglhldlksjfakgshfkfgfdsjdfgdgflfsskfssjflahlsjkkkgdflfaghdsajdhgglklsfgjjk",
        "lkjahsdflkjhasldkjfhlkjasghkjfkjsahdfkljhakjsdhfkjlahsdkfghaksgdfkjagsdfkjgasdkjfhaskjdgfkhjagfkasgdfkljagsdkfgaksgkasgdfklasgdfkjgaskjdfgkajsgdfkljagskdjfghakslgdfklagsfdkljgaskjldgfklagsdfjgaksldjfgklasgdfklagsdklfjgakjsdgfkljasgdkfljgaskljdfgkljasgdfkgsaklfgajksgdflkagsdflkgalskdfgasgdfkjashgdfhjagsdfjhgjahksgdfjhkasgdfjhgasjdhfkgajkhsdgfjkhagsdjfkhgasjkdhgfjkashdgfjkhgasdjkfhgasjkhdgfjkhagsdfjkhagsdfjhkgasjkdfgjakshdgfj",
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab",
        //"lkajsgdfas",
        //"fgdgksflfgkklhjshgljjjaaklfglasjhajjgsjfgafafhhkjkkagdjlhgksdsggshhsllkadfkhddkfgghjadfkjghhjkjskghsgkhdgjkffkgsssjjdajkdhjadhfkljgsjhallskdjaflfdsgdsfgkjkgsskshjalasggldgdldhllgajdahgklfasjahgsfadgsghgfkgahsfslfdhhgjfahdjkhhjjjjagsffdkakkdjhasgkldshdlkakjsaaakljdghkfkgaaadkgdkgdjhfslkdgskhfakjgdddgllflllfsaaafkagjffllffjgsdhldljjsfkjafahldjkfljfgkggjjdssaafhhjfgglhdgdkhgkjhadsffshdjdajdjsfjghlkgafjdaaajdaghlsjlakhdjsdlssff",

        //"pjzhong",
        //"pjzhong",
        //"pjzhong",
        //"cars",

    };
    String[][] disc = {
        {"aaaa", "aa"},
        {"bc", "cb"},
        {"pj", "jz", "ng", "ong", "zh"},
        {"cc", "ac"},
        {"apple", "pen"},
        {"cats", "dog", "sand", "and", "cat"},
        {"leet", "code"},
        {"pj", "ong", "zh"},
        {"p", "j", "z", "h", "o", "n"},
        {"kfomka", "hecagbngambii", "anobmnikj", "c", "nnkmfelneemfgcl", "ah", "bgomgohl", "lcbjbg",
            "ebjfoiddndih", "hjknoamjbfhckb", "eioldlijmmla", "nbekmcnakif", "fgahmihodolmhbi",
            "gnjfe", "hk", "b", "jbfgm", "ecojceoaejkkoed", "cemodhmbcmgl", "j", "gdcnjj",
            "kolaijoicbc", "liibjjcini", "lmbenj", "eklingemgdjncaa", "m", "hkh", "fblb", "fk",
            "nnfkfanaga", "eldjml", "iejn", "gbmjfdooeeko", "jafogijka", "ngnfggojmhclkjd",
            "bfagnfclg", "imkeobcdidiifbm", "ogeo", "gicjog", "cjnibenelm", "ogoloc", "edciifkaff",
            "kbeeg", "nebn", "jdd", "aeojhclmdn", "dilbhl", "dkk", "bgmck", "ohgkefkadonafg",
            "labem", "fheoglj", "gkcanacfjfhogjc", "eglkcddd", "lelelihakeh", "hhjijfiodfi",
            "enehbibnhfjd", "gkm", "ggj", "ag", "hhhjogk", "lllicdhihn", "goakjjnk", "lhbn",
            "fhheedadamlnedh", "bin", "cl", "ggjljjjf", "fdcdaobhlhgj", "nijlf", "i", "gaemagobjfc",
            "dg", "g", "jhlelodgeekj", "hcimohlni", "fdoiohikhacgb", "k", "doiaigclm",
            "bdfaoncbhfkdbjd", "f", "jaikbciac", "cjgadmfoodmba", "molokllh", "gfkngeebnggo",
            "lahd", "n", "ehfngoc", "lejfcee", "kofhmoh", "cgda", "de", "kljnicikjeh",
            "edomdbibhif", "jehdkgmmofihdi", "hifcjkloebel", "gcghgbemjege", "kobhhefbbb",
            "aaikgaolhllhlm", "akg", "kmmikgkhnn", "dnamfhaf", "mjhj", "ifadcgmgjaa",
            "acnjehgkflgkd", "bjj", "maihjn", "ojakklhl", "ign", "jhd", "kndkhbebgh",
            "amljjfeahcdlfdg", "fnboolobch", "gcclgcoaojc", "kfokbbkllmcd", "fec", "dljma", "noa",
            "cfjie", "fohhemkka", "bfaldajf", "nbk", "kmbnjoalnhki", "ccieabbnlhbjmj",
            "nmacelialookal", "hdlefnbmgklo", "bfbblofk", "doohocnadd", "klmed", "e",
            "hkkcmbljlojkghm", "jjiadlgf", "ogadjhambjikce", "bglghjndlk", "gackokkbhj",
            "oofohdogb", "leiolllnjj", "edekdnibja", "gjhglilocif", "ccfnfjalchc", "gl", "ihee",
            "cfgccdmecem", "mdmcdgjelhgk", "laboglchdhbk", "ajmiim", "cebhalkngloae",
            "hgohednmkahdi", "ddiecjnkmgbbei", "ajaengmcdlbk", "kgg", "ndchkjdn", "heklaamafiomea",
            "ehg", "imelcifnhkae", "hcgadilb", "elndjcodnhcc", "nkjd", "gjnfkogkjeobo", "eolega",
            "lm", "jddfkfbbbhia", "cddmfeckheeo", "bfnmaalmjdb", "fbcg", "ko", "mojfj", "kk",
            "bbljjnnikdhg", "l", "calbc", "mkekn", "ejlhdk", "hkebdiebecf", "emhelbbda", "mlba",
            "ckjmih", "odfacclfl", "lgfjjbgookmnoe", "begnkogf", "gakojeblk", "bfflcmdko",
            "cfdclljcg", "ho", "fo", "acmi", "oemknmffgcio", "mlkhk", "kfhkndmdojhidg",
            "ckfcibmnikn", "dgoecamdliaeeoa", "ocealkbbec", "kbmmihb", "ncikad", "hi",
            "nccjbnldneijc", "hgiccigeehmdl", "dlfmjhmioa", "kmff", "gfhkd", "okiamg", "ekdbamm",
            "fc", "neg", "cfmo", "ccgahikbbl", "khhoc", "elbg", "cbghbacjbfm", "jkagbmfgemjfg",
            "ijceidhhajmja", "imibemhdg", "ja", "idkfd", "ndogdkjjkf", "fhic", "ooajkki", "fdnjhh",
            "ba", "jdlnidngkfffbmi", "jddjfnnjoidcnm", "kghljjikbacd", "idllbbn", "d",
            "mgkajbnjedeiee", "fbllleanknmoomb", "lom", "kofjmmjm", "mcdlbglonin", "gcnboanh",
            "fggii", "fdkbmic", "bbiln", "cdjcjhonjgiagkb", "kooenbeoongcle", "cecnlfbaanckdkj",
            "fejlmog", "fanekdneoaammb", "maojbcegdamn", "bcmanmjdeabdo", "amloj", "adgoej", "jh",
            "fhf", "cogdljlgek", "o", "joeiajlioggj", "oncal", "lbgg", "elainnbffk", "hbdi",
            "femcanllndoh", "ke", "hmib", "nagfahhljh", "ibifdlfeechcbal", "knec",
            "oegfcghlgalcnno", "abiefmjldmln", "mlfglgni", "jkofhjeb", "ifjbneblfldjel",
            "nahhcimkjhjgb", "cdgkbn", "nnklfbeecgedie", "gmllmjbodhgllc", "hogollongjo",
            "fmoinacebll", "fkngbganmh", "jgdblmhlmfij", "fkkdjknahamcfb", "aieakdokibj",
            "hddlcdiailhd", "iajhmg", "jenocgo", "embdib", "dghbmljjogka", "bahcggjgmlf", "fb",
            "jldkcfom", "mfi", "kdkke", "odhbl", "jin", "kcjmkggcmnami", "kofig", "bid", "ohnohi",
            "fcbojdgoaoa", "dj", "ifkbmbod", "dhdedohlghk", "nmkeakohicfdjf", "ahbifnnoaldgbj",
            "egldeibiinoac", "iehfhjjjmil", "bmeimi", "ombngooicknel", "lfdkngobmik",
            "ifjcjkfnmgjcnmi", "fmf", "aoeaa", "an", "ffgddcjblehhggo", "hijfdcchdilcl",
            "hacbaamkhblnkk", "najefebghcbkjfl", "hcnnlogjfmmjcma", "njgcogemlnohl", "ihejh", "ej",
            "ofn", "ggcklj", "omah", "hg", "obk", "giig", "cklna", "lihaiollfnem", "ionlnlhjckf",
            "cfdlijnmgjoebl", "dloehimen", "acggkacahfhkdne", "iecd", "gn", "odgbnalk", "ahfhcd",
            "dghlag", "bchfe", "dldblmnbifnmlo", "cffhbijal", "dbddifnojfibha", "mhh", "cjjol",
            "fed", "bhcnf", "ciiibbedklnnk", "ikniooicmm", "ejf", "ammeennkcdgbjco", "jmhmd", "cek",
            "bjbhcmda", "kfjmhbf", "chjmmnea", "ifccifn", "naedmco", "iohchafbega", "kjejfhbco",
            "anlhhhhg"},
        {"a", "s", "d", "f", "g", "h", "j", "k",},
        {"a", "s", "d", "f", "g", "h", "j", "k", "l"},
        {"a", "aa", "aaa", "aaaa", "aaaaa", "aaaaaa", "aaaaaaa", "aaaaaaaa", "aaaaaaaaa",
            "aaaaaaaaaa"},
    };

    Boolean[] booleans = {
        false,// aaaaaaa
        false,//ccbb
        true,// pjzhongzh
        true,// ccaccc
        true,// applepenapple
        false,// catsandog
        true,//leetcode
        true,// pjzhong
        false,// pjzhong
        true,//fohhemkkaecojceoaejkkoedkofhmohkcjmkggcmnami
        false,
//aagdggdhdlgksklhghgdhkgakahjdhjsjfkkggljaksadajaskjlljlglfahjajlaasaksjsfasddaklggjssglglhfhjjglgjllfjsfslalhlskggfjfsghgfffjkgkhjjdhhjfdfgdhsdhfggfgadhsgglaahldsfjllahlklfdsdsglgfhjkdsllhsfdkljldkjlghkkdakadfflkjajshssdkdkjafkjadglkslkaksgfdlfglfjgkskdkaaaagdksasgldkflfsshlaffkfflkgkakashhdssjgsahfkagllhfsaffsfgsjlfkjjafssfggdhkaksdadfaaafldafffaffgdsglhldlksjfakgshfkfgfdsjdfgdgflfsskfssjflahlsjkkkgdflfaghdsajdhgglklsfgjjk
        true,
//lkjahsdflkjhasldkjfhlkjasghkjfkjsahdfkljhakjsdhfkjlahsdkfghaksgdfkjagsdfkjgasdkjfhaskjdgfkhjagfkasgdfkljagsdkfgaksgkasgdfklasgdfkjgaskjdfgkajsgdfkljagskdjfghakslgdfklagsfdkljgaskjldgfklagsdfjgaksldjfgklasgdfklagsdklfjgakjsdgfkljasgdkfljgaskljdfgkljasgdfkgsaklfgajksgdflkagsdflkgalskdfgasgdfkjashgdfhjagsdfjhgjahksgdfjhkasgdfjhgasjdhfkgajkhsdgfjkhagsdjfkhgasjkdhgfjkashdgfjkhgasdjkfhgasjkhdgfjkhagsdfjkhagsdfjhkgasjkdfgjakshdgfj
        false,
//aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab
    };

    for (int i = 0; i < worlds.length; i++) {
      String s = worlds[i];
      Boolean b = booleans[i], result = null;
      try {
        logger.info("{} {} b:{} started", s, Arrays.toString(disc[i]), b);
        result = wordBreak(s, Arrays.asList(disc[i]));
        System.out.format("%s-%s-%s\n", result, s, Arrays.toString(disc[i]));
      } catch (Exception e) {
        logger.error(s, e);
      }

      Assert.assertEquals(b, result);
    }
  }

  /**
   * 将s分解为n个wordDict所包含的字符串, wordDict的字符串可以复用
   *
   * @return true:s能被完全分解, false:s不能被完全分解
   * @since 2020年06月20日 17:12:16
   */
  public boolean wordBreak(String s, List<String> wordDict) {
    //return wordBreak(0, new StringBuilder(s), new HashSet<>(), wordDict);
    //return wordBreak(new StringBuilder(s), new StringBuilder(), new HashSet<>(), wordDict);
    return wordBreak(s, 0, new boolean[s.length()], wordDict);
    //return wordBreak(sb, wordDict);
  }

  // The Best Practice
  public boolean wordBreak(String builder, int idx, boolean[] booleans,
      List<String> wordDict) {
    if (builder.length() <= idx) {
      return true;
    }

    for (String word : wordDict) {
      if (builder.startsWith(word, idx)) {
        idx += word.length();
        if (!booleans[idx - 1]) {
          booleans[idx - 1] = true;
          if (wordBreak(builder, idx, booleans, wordDict)) {
            return true;
          }
        }
        idx -= word.length();
      }
    }

    return booleans[builder.length() - 1];
  }

  public boolean wordBreak(StringBuilder builder, StringBuilder pathBuilder, Set<String> paths,
      List<String> wordDict) {
    if (builder.length() == 0) {
      return true;
    }

    logger.info("{}", builder);
    for (String word : wordDict) {
      if (equals(builder, word)) {
        if (paths.add(pathBuilder.append(word).toString())) {
          builder.replace(0, word.length(), "");
          if (wordBreak(builder, pathBuilder, paths, wordDict)) {
            return true;
          }
          builder.insert(0, word);
        }
        int length = pathBuilder.length();
        pathBuilder.replace(length - word.length(), length, "");
      }
    }

    return false;
  }

  private boolean equals(CharSequence builder, CharSequence world) {
    if (world.length() <= builder.length()) {
      for (int i = 0, size = world.length(); i < size; i++) {
        if (builder.charAt(i) != world.charAt(i)) {
          return false;
        }
      }

      return true;
    }

    return false;
  }
}
