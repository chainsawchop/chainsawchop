package chainsawchop

import spock.lang.Specification

class Chop__ASpec extends Specification {

    def "basic1"() {
        expect:
        Chop.init(inputstr).chop(chopstr).head == head
        Chop.init(inputstr).chop(chopstr).mid == mid
        Chop.init(inputstr).chop(chopstr).tail == tail

        where:
        inputstr    | chopstr     || head   | mid         | tail
        'somewhere' | 'om'        || 's'    | 'om'        | 'ewhere'
        'somewhere' | 's'         || ''     | 's'         | 'omewhere'
        'somewhere' | 'somewhere' || ''     | 'somewhere' | ''
        'somewhere' | 'where'     || 'some' | 'where'     | ''
        'somewhere' | 'e'         || 'som'  | 'e'         | 'where'
        'somewhere' | 'zz'        || ''     | ''          | ''
        'somewhere' | ''          || ''     | ''          | 'somewhere'   // <-- desired result?
        'somewhere' | null        || ''     | ''          | ''   // <-- desired result?
    }

    def "chop_head_chop_1"() {
        expect:
        Chop c2 = Chop.init(inputstr).chop(chop1).head().chop(chop2)
        c2.head == head
        c2.mid == mid
        c2.tail == tail

        where:
        inputstr    | chop1   | chop2 || head | mid  | tail
        'somewhere' | 'where' | 'om'  || 's'  | 'om' | 'e'
    }

    def "chop_mid_chop_1"() {
        expect:
        Chop c2 = Chop.init(inputstr).chop(chop1).mid().chop(chop2)
        c2.head == head
        c2.mid == mid
        c2.tail == tail

        where:
        inputstr    | chop1   | chop2 || head | mid  | tail
        'somewhere' | 'where' | 'er'  || 'wh' | 'er' | 'e'
        'somewhere' | 'mewhe' | 'her' || ''   | ''   | ''
    }

    def "chop_tail_chop_1"() {
        expect:
        Chop c2 = Chop.init(inputstr).chop(chop1).tail().chop(chop2)
        c2.head == head
        c2.mid == mid
        c2.tail == tail

        where:
        inputstr    | chop1  | chop2 || head | mid | tail
        'somewhere' | 'mewh' | 'r'   || 'e'  | 'r' | 'e'
    }

    def "chop_chop_1"() {
        expect:
        Chop c2 = Chop.init(inputstr).chop(chop1).chop(chop2)
        c2.head == head
        c2.mid == mid
        c2.tail == tail

        where:
        inputstr    | chop1  | chop2 || head | mid | tail
        'somewhere' | 'mewh' | 'r'   || 'e'  | 'r' | 'e'
    }

    // TODO: gobble vs gobbleif: gobble should consume all, if the match isn't found, everything is in head, gobbleif: match isn't found, then tail acts as if nothing was chopped / gives original string

    // TODO: gobbleRx / gobbleRxIf: might be unnecessary with rx greedy operators....
}
