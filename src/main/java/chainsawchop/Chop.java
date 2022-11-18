package chainsawchop;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// backwards?
// chopAny
// segment (extract from the swing package) and char sequences rather than strings


public class Chop {
     String orig = "";
    int hdStrtI = -1;
    int hdStopI = -1;
    int mdStrtI = -1;
    int mdStopI = -1;
    int tlStrtI = -1;
    int tlStopI = -1;

    static Pattern whitespace = Pattern.compile("\\s+");

    public String[] parts() {
        String[] sarr = new String[3];
        sarr[0] = getHead();
        sarr[1] = getMid();
        sarr[2] = getTail();
        return sarr;
    }

    public Chop[] partsAsChop() {
        Chop[] sarr = new Chop[3];
        sarr[0] = head();
        sarr[1] = mid();
        sarr[2] = tail();
        return sarr;
    }

    public static Chop init(String s) {
        Chop chop = new Chop();
        chop.orig = s;
        chop.hdStrtI = 0;
        chop.hdStopI = 0;
        chop.mdStrtI = 0;
        chop.mdStopI = 0;
        chop.tlStrtI = 0;
        chop.tlStopI = s.length();
        return chop;
    }

    public Chop chop(String substr) {
        int idx = StringUtils.indexOf(orig, substr, tlStrtI);
        if (idx >= 0) {
            if (idx + substr.length() <= tlStopI) {
                Chop chop = new Chop();
                chop.orig = orig;
                chop.hdStrtI = tlStrtI;
                chop.hdStopI = idx;
                chop.mdStrtI = idx;
                chop.mdStopI = idx + substr.length();
                chop.tlStrtI = idx + substr.length();
                chop.tlStopI = tlStopI;
                return chop;
            }
        }
        Chop chop = new Chop();
        return chop;
    }

    public boolean empty() {
        return "".equals(getHead()) && "".equals(getMid()) && "".equals(getTail());
    }

    public Chop chopAll(String substr) {
        Chop curchop = this;
        boolean firstchop = true;
        while (true) {
            Chop forechop = curchop.chop(substr);
            if (firstchop && forechop.empty()) return forechop;
            if (forechop.empty()) return curchop;
            firstchop = false;
        }
    }

    public Chop chopAllIf(String substr) {
        Chop curchop = this;
        while (true) {
            Chop forechop = curchop.chop(substr);
            if (forechop.empty()) return curchop;
        }
    }

    public Chop chopIf(String substr) {
        return !getTail().contains(substr) ? this : chop(substr);
    }

    public Chop nchop(String substr, int times) {
        if (times < 1) times = 1;
        Chop curchop = this;
        for (int i = 0; i < times; i++) {
            if (curchop.empty()) return curchop;
            curchop = curchop.chop(substr);
        }
        return curchop;
    }

    public Chop chopRx(String p) {
        return chopRx(Pattern.compile(p));
    }

    // use java.desktop.Segment? currently we have to do substring copy to do this
    public Chop chopRx(Pattern p) {
        String src = getTail();
        Matcher m = p.matcher(src);
        boolean found = m.find();
        if (found) {
            int matchStartIdx = m.start();
            int matchEndIdx = m.end();
            Chop chop = new Chop();
            chop.orig = orig;
            chop.hdStrtI = tlStopI;
            chop.hdStopI = tlStopI + m.start();
            chop.mdStrtI = tlStopI + m.start();
            chop.mdStopI = tlStopI + m.end();
            chop.tlStrtI = tlStopI + m.end();
            chop.tlStopI = tlStopI + src.length();
            return chop;
        } else {
            Chop chop = new Chop();
            chop.orig = orig;
            chop.hdStrtI = 0;
            chop.hdStopI = 0;
            chop.mdStrtI = 0;
            chop.mdStopI = 0;
            chop.tlStrtI = 0;
            chop.tlStopI = 0;
            return chop;
        }
    }

    public Chop chopRxIf(String p) {
        return chopRxIf(Pattern.compile(p));
    }

    public Chop chopRxIf(Pattern p) {
        String src = getTail();
        Matcher m = p.matcher(src);
        boolean found = m.find();
        if (found) {
            int matchStartIdx = m.start();
            int matchEndIdx = m.end();
            Chop chop = new Chop();
            chop.orig = orig;
            chop.hdStrtI = tlStopI;
            chop.hdStopI = tlStopI + m.start();
            chop.mdStrtI = tlStopI + m.start();
            chop.mdStopI = tlStopI + m.end();
            chop.tlStrtI = tlStopI + m.end();
            chop.tlStopI = tlStopI + src.length();
            return chop;
        } else {
            return this;
        }
    }

    public Chop chopToken() {
        return chopRx(whitespace);
    }

    public Chop head() {
        Chop headchop = new Chop();
        headchop.orig = orig;
        headchop.hdStrtI = hdStrtI;
        headchop.hdStopI = hdStrtI;
        headchop.mdStrtI = hdStrtI;
        headchop.mdStopI = hdStrtI;
        headchop.tlStrtI = hdStrtI;
        headchop.tlStopI = hdStopI;
        return headchop;
    }

    public Chop mid() {
        Chop headchop = new Chop();
        headchop.orig = orig;
        headchop.hdStrtI = mdStrtI;
        headchop.hdStopI = mdStrtI;
        headchop.mdStrtI = mdStrtI;
        headchop.mdStopI = mdStrtI;
        headchop.tlStrtI = mdStrtI;
        headchop.tlStopI = mdStopI;
        return headchop;

    }

    public Chop tail() {
        Chop headchop = new Chop();
        headchop.orig = orig;
        headchop.hdStrtI = tlStrtI;
        headchop.hdStopI = tlStrtI;
        headchop.mdStrtI = tlStrtI;
        headchop.mdStopI = tlStrtI;
        headchop.tlStrtI = tlStrtI;
        headchop.tlStopI = tlStopI;
        return headchop;
    }

    public String getHead() {
        return StringUtils.substring(orig, hdStrtI, hdStopI);
    }

    public String getMid() {
        return StringUtils.substring(orig, mdStrtI, mdStopI);
    }

    public String getTail() {
        return StringUtils.substring(orig, tlStrtI, tlStopI);
    }

    // gets the head of the original string that Chop was initialized with
    // so if you navigate using a sequence of chop head/mid/tails chained
    // to "navigate" to a chop point in a string, you can still get the
    // overall string head
    public String getHeadOriginal() {
        return StringUtils.substring(orig, 0, mdStrtI);
    }

    // equivalent of getHeadOriginal() but the tail portion
    public String getTailOriginal() {
        return StringUtils.substring(orig, mdStopI);
    }


}
