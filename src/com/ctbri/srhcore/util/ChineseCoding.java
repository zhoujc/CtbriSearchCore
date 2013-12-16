package com.ctbri.srhcore.util;


/**
 * ��ϵͳ��ֱ������
 *
 */
public class ChineseCoding
{
  private static String SimplifiedCnStr = "�������������������������������������������������������������������°ðİŰưǰȰɰʰ˰̰ͰΰϰаѰҰӰ԰հְװذٰڰ۰ܰݰް߰�������������������������������������������������������������������������������������������������������������±ñıűƱǱȱɱʱ˱̱ͱαϱбѱұӱԱձֱױرٱڱ۱ܱݱޱ߱�������������������������������������������������������������������������������������������������������������²òĲŲƲǲȲɲʲ˲̲ͲβϲвѲҲӲԲղֲײزٲڲ۲ܲݲ޲߲�������������������������������������������������������������������������������������������������������������³óĳųƳǳȳɳʳ˳̳ͳγϳгѳҳӳԳճֳ׳سٳڳ۳ܳݳ޳߳��������������������������������������������������������������������������������������������������������������´ôĴŴƴǴȴɴʴ˴̴ʹδϴдѴҴӴԴմִ״شٴڴ۴ܴݴ޴ߴ�������������������������������������������������������������������������������������������������������������µõĵŵƵǵȵɵʵ˵̵͵εϵеѵҵӵԵյֵ׵صٵڵ۵ܵݵ޵ߵ�������������������������������������������������������������������������������������������������������������¶öĶŶƶǶȶɶʶ˶̶Ͷζ϶жѶҶӶԶնֶ׶ضٶڶ۶ܶݶ޶߶�������������������������������������������������������������������������������������������������������������·÷ķŷƷǷȷɷʷ˷̷ͷηϷзѷҷӷԷշַ׷طٷڷ۷ܷݷ޷߷�������������������������������������������������������������������������������������������������������������¸øĸŸƸǸȸɸʸ˸̸͸θϸиѸҸӸԸոָ׸ظٸڸ۸ܸݸ޸߸�������������������������������������������������������������������������������������������������������������¹ùĹŹƹǹȹɹʹ˹̹͹ιϹйѹҹӹԹչֹ׹عٹڹ۹ܹݹ޹߹�������������������������������������������������������������������������������������������������������������ºúĺźƺǺȺɺʺ˺̺ͺκϺкѺҺӺԺպֺ׺غٺںۺܺݺ޺ߺ�������������������������������������������������������������������������������������������������������������»ûĻŻƻǻȻɻʻ˻̻ͻλϻлѻһӻԻջֻ׻ػٻڻۻܻݻ޻߻�������������������������������������������������������������������������������������������������������������¼üļżƼǼȼɼʼ˼̼ͼμϼмѼҼӼԼռּ׼ؼټڼۼܼݼ޼߼�������������������������������������������������������������������������������������������������������������½ýĽŽƽǽȽɽʽ˽̽ͽνϽнѽҽӽԽսֽ׽ؽٽھ����������������������������������������������������������¾þľžƾǾȾɾʾ˾̾;ξϾоѾҾӾԾվ־׾ؾپھ۾ܾݾ޾߾�����������������������۽ܽݽ޽߽�����������������������������������������������������������������������������������������������������������������������������������������¿ÿĿſƿǿȿɿʿ˿̿ͿοϿпѿҿӿԿտֿ׿ؿٿڿۿܿݿ޿߿����������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿������������������������������������������������������������������������������������������������������������������������������áâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ������������������������������������������������������������������������������������������������������������������������������ġĢģĤĥĦħĨĩĪīĬĭĮįİıĲĳĴĵĶķĸĹĺĻļĽľĿ������������������������������������������������������������������������������������������������������������������������������šŢţŤťŦŧŨũŪūŬŭŮůŰűŲųŴŵŶŷŸŹźŻżŽžſ������������������������������������������������������������������������������������������������������������������������������ơƢƣƤƥƦƧƨƩƪƫƬƭƮƯưƱƲƳƴƵƶƷƸƹƺƻƼƽƾƿ������������������������������������������������������������������������������������������������������������������������������ǢǣǤǥǦǧǨǩǪǫǬǭǮǯǰǱǲǳǴǵǶǷǸǹǺǻǼǽǾǿ������������������������������������������������������������������������������������������������������������������������������ȡȢȣȤȥȦȧȨȩȪȫȬȭȮȯȰȱȲȳȴȵȶȷȸȹȺȻȼȽȾȿ������������������������������������������������������������������������������������������������������������������������������ɡɢɣɤɥɦɧɨɩɪɫɬɭɮɯɰɱɲɳɴɵɶɷɸɹɺɻɼɽɾɿ������������������������������������������������������������������������������������������������������������������������������ʡʢʣʤʥʦʧʨʩʪʫʬʭʮʯʰʱʲʳʴʵʶʷʸʹʺʻʼʽʾʿ������������������������������������������������������������������������������������������������������������������������������ˡˢˣˤ˥˦˧˨˩˪˫ˬ˭ˮ˯˰˱˲˳˴˵˶˷˸˹˺˻˼˽˾˿������������������������������������������������������������������������������������������������������������������������������̴̵̶̷̸̡̢̧̨̣̤̥̦̩̪̫̬̭̮̯̰̱̲̳̹̺̻̼̽̾̿������������������������������������������������������������������������������������������������������������������������������ͣͤͥͦͧͨͩͪͫͬͭͮͯ͢͡ͰͱͲͳʹ͵Ͷͷ͸͹ͺͻͼͽ;Ϳ������������������������������������������������������������������������������������������������������������������������������Ρ΢ΣΤΥΦΧΨΩΪΫάέήίΰαβγδεζηθικλμνξο������������������������������������������������������������������������������������������������������������������������������ϡϢϣϤϥϦϧϨϩϪϫϬϭϮϯϰϱϲϳϴϵ϶ϷϸϹϺϻϼϽϾϿ������������������������������������������������������������������������������������������������������������������������������СТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмноп������������������������������������������������������������������������������������������������������������������������������ѡѢѣѤѥѦѧѨѩѪѫѬѭѮѯѰѱѲѳѴѵѶѷѸѹѺѻѼѽѾѿ������������������������������������������������������������������������������������������������������������������������������ҡҢңҤҥҦҧҨҩҪҫҬҭҮүҰұҲҳҴҵҶҷҸҹҺһҼҽҾҿ������������������������������������������������������������������������������������������������������������������������������ӡӢӣӤӥӦӧӨөӪӫӬӭӮӯӰӱӲӳӴӵӶӷӸӹӺӻӼӽӾӿ������������������������������������������������������������������������������������������������������������������������������ԡԢԣԤԥԦԧԨԩԪԫԬԭԮԯ԰ԱԲԳԴԵԶԷԸԹԺԻԼԽԾԿ������������������������������������������������������������������������������������������������������������������������������աբգդեզէըթժիլխծկհձղճմյնշոչպջռսվտ������������������������������������������������������������������������������������������������������������������������������ְֱֲֳִֵֶַָֹֺֻּֽ֢֣֤֥֦֧֪֭֮֡֨֩֫֬֯־ֿ������������������������������������������������������������������������������������������������������������������������������סעףפץצקרשת׫׬׭׮ׯװױײ׳״׵׶׷׸׹׺׻׼׽׾׿��������������������������������������������������������������������������������������������������������������������";
  private static String TraditionalCnStr = "���������������}���@�����K�۰����������������������������������\���W�ðİŰưǰȰɰʰ˰̰ͰΰϰаѰ҉ΰ��T�ְװذٔ[�۔��ݰް߰�����C���������k�O��Ͱ��򽉰������^���r����������������������󱩱��U������������݅��ؐ�^���N��v�����������������±ñıűƱǱȱɹP�˱̱ͱή����юűӱ��]�ֱױرٱڱ۱ܱݱ�߅���H���׃����q�p��˱����M��e�T���l�I�e�P��������������K�������������������K�����������������g���N���a�����������������²òĲ�ؔ�ǲȲɲʲ˲̲ͅ��Q���M�K�N�nœ�}��زٲڲ۲ܲݎ��߂ȃԜy�Ӳ������������Ԍ����v���s�׋�p�P�a�U������L���L���c�S�������������n������������܇�������س��������m������ꐳ��r�ηQ�ǳȳɳʳ˳̑ͳ��\�г��G�ӳ԰V�ֳ׳��t���Y�u�X�޳߳������n�n�x�猙��ꮠ�P����I��I���h���������N���z�r�������A�������|̎�������������������������J���������N�������������������b�ôĴŴ��o�ȴ��~�˴��n���[��ҏą����ִ״ش��f�۸Z�ݴ޴ߴ��������������e���_�������������������J��������������������đ�����������Q���������hʎ�n���v�����u�\�����������I�µõĵş��ǵȵɵ����̵͵εϔ��ѵҜ�Եյֵ׵صٵڵ۵��f������c���|늵�����յ�����������{����������ՙ�B�������픶��V��ӆ�G�|�������ӗ��������������Y���������������٪��x�¶�ـ��僶Ƕȶɶʶ˶�呶Δ྄�у�ꠌ��Շ��׶��D���g�ܶݶ޶߶��Z�������艙����Z���~Ӟ�𐺶������I�����������D�����E�l�P�������y���m�����������\�C������������؜��������������������L���ŷƷǷ��w�ʷ��u�ͷΏU���M�ҷӷԷշּ����ٷڷۊ^�ݷޑ��S�S�������h�L������T�p�S���P������w������ݗ���������������������������o���������������������x�}����������ؓ��Ӈ���D�`������ԓ�ĸ��}�w�Ȏָʸ˸̸͸��s�ж����M����䓸׸ؾV���۸ܸݸ޸߸����怸����R��������������w���t�����o�����������������������������������m��얹���ؕ���^���Ϲ�������ُ�򹼹����������¹ùĹ��M�ǹȹɹ�̹͹ιτ��ђ�ӹԹչֹ��P�ٹ��^���^�ޑT��؞��V���Ҏ�����w���|܉��Ԏ��𙙹��F��݁�L��偹��������^��������������񔺨�����n�����������������������������h���������������ºú�̖�ƺǺȺɺʺ˺̺ͺκϺк��u�ӺԺպ��Q�R�ٺںۺܺݺ޺ߺ��M����Z����������t������������������غ������������������o���������W�A����������Ԓ�����ѻ��Ěg�h��߀���Q�����������o�»ûĻ��S�ǻȻɻʻ˻̻ͻλϻ��e�ғ]�x�ջֻ׻ؚ��ڻۻܻݻ��V�x���Z�R�M�d�Lȝ���꜆�����ⷻ�@����؛���������C�����e�������E���I�u���������O��݋�������������������D�׼����E���������������ļ�Ӌӛ�ȼ��H���^�o�μϊA�ѼҼ��v�a�Z��⛼ټڃr���{�ޚ��O�Լ�{�g�����D��}�O�z��A�|���캆�����p�]���b�`�vҊ�I������Ş���T�u�R�����������{�����Y�����v���u�����������z�������ɽ����q�C�e�_�ƽ���U�g�˽̽��I�^�нѽҽӽԽս��A�ؽٹ��o�����L���@�������������i�o�����R���d�������Q���������m���¾þľžƎ����f�ʾ˾̾;ξϾоѾҾ��x�վ־׾��e�ھ۾ܓ��޾߾��䏾��־愡���N���������������ۂܽݽ޽ߝ��Y�����������]�ý��������o�\�H֔�M���x�����a���M���G���X�Q�E�^�����x܊�������������E���������_�����P�����������������������������������������¿ÿ��w�ƚ��ȿɿʿ˿̿��n�ϿЉ����ӿԿտֿ׿ؓ��ڿۿܿݿ޿߿���ѝ�F�����K��~�쌒������V����r̝���h�Q�������������������������U���������Ϟ�D�����R��ه�{���ڔr�@�@�m��׎���[���|���E�������������˓Ƅ������������ӝ��՘����D�������܉�����I��������������h���x�������Y���Y��������������[�����������������r�`�������zɏ�B����z�i����Ę朑ٟ����Z�����������v������Տ�����ů������|������������������ӫC���������R���[�܄C�U�����������g�����R�`��X�I�������������s�������������@���\��¡�Ŕn�]�Ǌ䓧�t©ª�J�R�B�]�t���u̔��´µ¶·�T¹º����¾�H���X�H���Čҿ|�]�����ʞV�G�n���\���сy���Ԓ�݆�����S�]Փ�}���_߉茻j��������j���鬔�aΛ�R�R������I���u�~�}�m�z�U�M��������֙âãäåæç؈é�^ëì�Tîïðñò�Q�Nõö÷ø�qú�]üý�Vÿ�����������T�������������i�͉��������������i������Ғ�����܃����߾d�������侒��������������R����������������}�����Q�����և��ġĢģĤĥĦħĨĩĪīĬĭĮįİ�\ĲĳĴĵ��ķĸĹĺĻļĽľĿ�������������c���ȼ{���������������y�ғ��X���[�����H����������������M����ā������������f��������B�������m������������帔Q��ţŤ�o�~ē���rŪūŬŭŮůŰ��ŲųŴ�ZŶ�W�t��ź�Iż�ažſ�����������������������˱P�������������������֒����������������������r�������懊�������������������������i��������������������ơƢƣƤƥƦƧƨƩƪƫƬ�_�hƯưƱƲƳƴ�lؚƷƸƹƺ�OƼƽ�{ƿ�u�����H�����������ʓ�䁃W���������Ҙ��������V�������ۗ������ߜD������������������Ě�R�������T���M��������������������ә��Ǣ���L�T�Uǧ�w��Ǫ�tǬǭ�X�Qǰ��ǲ�\�l�qǶǷǸ����ǻǼ���N�������@���Ę��Ɔ̃S�������N���θ[�������Ӹ`�J���H�����������݌������p��A�������������Ո�c���F����������������څ�^�����|�����ȡȢ�xȤȥȦ�E��ȩȪȫȬȭȮȯ��ȱȲȳ�s�oȶ�_ȸȹȺȻȼȽȾȿ������׌���_�@�ǟ����������g���J���Ѽx�����������ؘs�������ݽq����������������������������ܛ�������J�c�����������_���w��ِ������ɢɣɤ��ɦ�}��ɩɪɫ��ɭɮɯɰ��ɲɳ��ɵɶɷ�Y��ɺɻɼɽ�hɿ���W���٠�������ȿ��ʂ����p�������������ԟ������������۽B���d��������z��������O�������������＝���򌏋����I���B���������Kʡʢʣ���}��ʧ�{ʩ��Ԋ��ʭʮʯʰ�rʲʳ�g���Rʷʸʹʺ�ʼʽʾʿ�����������ń��������m��������������ҕԇ�������؉��������ݫF�ߘ�������ݔ����������H��������������������g���������Q��������ˡˢˣˤ˥˦��˨˩˪�pˬ�lˮ˯��˱˲�˴�f�T˷�q˹˺˻˼˽˾�z��������������������Z������A�b���Ҕ\���K�������������������V�C�������m���S������q��������O�p�S�������s�����i�������������H��̧̨̣̤̥̦̩̪̫�B̭̮��؝�c����̴̵̶�TՄ̹̺̻̼̽�U̿�����������������������ˠC�͝��Ͻd����������ӑ�������v���`�������R���}�����w������������������������������l�������N�F���d �Nͣͤͥͦͧͨͩͪͫͬ͢͡�~ͮͯͰͱͲ�yʹ͵Ͷ�^͸͹�dͻ�Dͽ;�T���������ĈF���j��͑��������������Ó�r���W�E�����������ܸD�����m�����㏝�����B���������������������f�����������W������������Ρ΢Σ�f�`Φ��ΨΩ��H�SȔήί����β��δεζηθικλμ�^ξο�l���������y�Ƿ��Ɇ����̮Y��΁�u�C�����P�����׆��u���@�_�ݟoʏ���������������������]���F�����������`�������������������a��ϡϢϣϤϥϦϧϨϩϪϫϬϭ�uϯ��ϱϲ�ϴϵ϶��Ϲ�rϻϼݠϾ�{�b�M�B�ć����v�����r�w�y�t����e�������@�U�F�I�h���W�w�����޾���������������l����Ԕ����������������ʒ�����������N��������СТУФ�[ЦЧШЩЪϐЬ�f���yаб�{�C��ежзий�a�xмно�\����������������������d���������������������������ٛ����������������n�����C������̓�u����S����������������w�m܎�����������x�_ѣ�kѥѦ�WѨѩѪ��ѬѭѮԃ���ZѲѳѴӖӍ�dѸ��Ѻ�f��ѽѾѿ�����������ņ���Ӡ����鎟����}�����������������������������W�����������䏩�����V����������P������������W�B�������������u���b�G�{ҦҧҨˎҪҫҬҭҮ��ҰұҲ�Ҵ�I�~ҷҸҹҺһҼ�tҾ��������U���z�ƃx��������������ρ����������ˇ�������ك|�����������������㑛�x����Ԅ�h�x�g�������[���a������������y����������[ӡӢ�ы������t��Ξ�I��ωӭ�AӯӰ�fӲӳ�ѓ��ӷ�bӹӺ�xӼԁӾ�����������ă��Ƒn�����]♪q�����������������T������������������ݛ�������~����O��������c�Z�����Z�����������n�������R�����z���uԡԢԣ�Aԥ�S�x�YԩԪԫԬԭԮ�@�@�T�AԳԴ���hԷ�ԹԺԻ�sԽ�S耎[���������y���E���\�N�j����������s���՞����d�����۔���ٝ�E�v��������嗗������������������؟��t���\��������ٛ��������܈��lգ��եզէը�pժ�Sլխ��կհ��ղճմ�K��ݚ��չպ��ռ��վտ�`�������ď��Ɲq���Ɏ��~��Û�������������w�����������������U�H���N���@��������������ؑᘂ������\���������걠���b��������������֢���C֥֦֧֪֭֮֨֩֫֬��ֱֲֳ��ֵֶַָֹֺֻּ��־���S�����Î������������|���̜�������������ԽK�N�[���ٱ����������a���S�����䰙�敃�E���������i�T�D����T�������������������A�T�Bסעף�vץצק���u�D׫ٍ׭���f�b�yײ�Ѡ�׵�F׷٘���YՁ׼׽׾׿�������������Ɲ�Ɲ���Y�������������������ԝn������ۙ�ھC���v�u�����������������{��M�������������������������������";

  public static String SimplifyToTraditional(String str)
  {
    String tmpStr = "";
    for (int i = 0; i < str.length(); ++i)
      if (SimplifiedCnStr.indexOf(str.charAt(i)) != -1)
        tmpStr = tmpStr + TraditionalCnStr.charAt(SimplifiedCnStr.indexOf(str.charAt(i)));
      else
        tmpStr = tmpStr + str.charAt(i);


    return tmpStr;
  }

  public static String TraditionalToSimplify(String str)
  {
    String tmpStr = "";
    for (int i = 0; i < str.length(); ++i)
      if (TraditionalCnStr.indexOf(str.charAt(i)) != -1)
        tmpStr = tmpStr + SimplifiedCnStr.charAt(TraditionalCnStr.indexOf(str.charAt(i)));
      else
        tmpStr = tmpStr + str.charAt(i);


    return tmpStr;
  }

  public static String DBC2SBC(String str)
  {
    if (str == null) str = "";
    int width = str.length();
    StringBuffer sb = new StringBuffer();

    int i = 0;
    for (; (i < str.length()) && (i < width); ++i) {
      int c = str.charAt(i);

      if ((c >= 65281) && (c < 65374))
      {
        c = c - 65248;
      }

      if (c == 12288)
      {
        c = 32;
      }

      sb.append((char)c);
    }

    return sb.toString();
  }

  
  public static void main(String[] args)
  {
    String tmp = "�������f�����ģ��ƣģţãģңǣ������� ���£ãġ����������硡����������������";
    System.out.println(DBC2SBC(tmp));
    System.out.println(SimplifyToTraditional("���ķ���任����"));
    System.out.println(TraditionalToSimplify("�YӍ  �w��  �ʘ�  �Ƽ�  �ǱP "));
  }
}