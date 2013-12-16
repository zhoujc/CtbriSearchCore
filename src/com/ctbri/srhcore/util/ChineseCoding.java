package com.ctbri.srhcore.util;


/**
 * ¾ÉÏµÍ³ÖÐÖ±½ÓÄÃÀ´
 *
 */
public class ChineseCoding
{
  private static String SimplifiedCnStr = "°¡°¢°£°¤°¥°¦°§°¨°©°ª°«°¬°­°®°¯°°°±°²°³°´°µ°¶°·°¸°¹°º°»°¼°½°¾°¿°À°Á°Â°Ã°Ä°Å°Æ°Ç°È°É°Ê°Ë°Ì°Í°Î°Ï°Ð°Ñ°Ò°Ó°Ô°Õ°Ö°×°Ø°Ù°Ú°Û°Ü°Ý°Þ°ß°à°á°â°ã°ä°å°æ°ç°è°é°ê°ë°ì°í°î°ï°ð°ñ°ò°ó°ô°õ°ö°÷°ø°ù°ú°û°ü°ý°þ±¡±¢±£±¤±¥±¦±§±¨±©±ª±«±¬±­±®±¯±°±±±²±³±´±µ±¶±·±¸±¹±º±»±¼±½±¾±¿±À±Á±Â±Ã±Ä±Å±Æ±Ç±È±É±Ê±Ë±Ì±Í±Î±Ï±Ð±Ñ±Ò±Ó±Ô±Õ±Ö±×±Ø±Ù±Ú±Û±Ü±Ý±Þ±ß±à±á±â±ã±ä±å±æ±ç±è±é±ê±ë±ì±í±î±ï±ð±ñ±ò±ó±ô±õ±ö±÷±ø±ù±ú±û±ü±ý±þ²¡²¢²£²¤²¥²¦²§²¨²©²ª²«²¬²­²®²¯²°²±²²²³²´²µ²¶²·²¸²¹²º²»²¼²½²¾²¿²À²Á²Â²Ã²Ä²Å²Æ²Ç²È²É²Ê²Ë²Ì²Í²Î²Ï²Ð²Ñ²Ò²Ó²Ô²Õ²Ö²×²Ø²Ù²Ú²Û²Ü²Ý²Þ²ß²à²á²â²ã²ä²å²æ²ç²è²é²ê²ë²ì²í²î²ï²ð²ñ²ò²ó²ô²õ²ö²÷²ø²ù²ú²û²ü²ý²þ³¡³¢³£³¤³¥³¦³§³¨³©³ª³«³¬³­³®³¯³°³±³²³³³´³µ³¶³·³¸³¹³º³»³¼³½³¾³¿³À³Á³Â³Ã³Ä³Å³Æ³Ç³È³É³Ê³Ë³Ì³Í³Î³Ï³Ð³Ñ³Ò³Ó³Ô³Õ³Ö³×³Ø³Ù³Ú³Û³Ü³Ý³Þ³ß³à³á³â³ã³ä³å³å³æ³ç³è³é³ê³ë³ì³í³î³ï³ð³ñ³ò³ó³ô³õ³ö³÷³ø³ù³ú³û³ü³ý³þ´¡´¢´£´¤´¥´¦´§´¨´©´ª´«´¬´­´®´¯´°´±´²´³´´´µ´¶´·´¸´¹´º´»´¼´½´¾´¿´À´Á´Â´Ã´Ä´Å´Æ´Ç´È´É´Ê´Ë´Ì´Í´Î´Ï´Ð´Ñ´Ò´Ó´Ô´Õ´Ö´×´Ø´Ù´Ú´Û´Ü´Ý´Þ´ß´à´á´â´ã´ä´å´æ´ç´è´é´ê´ë´ì´í´î´ï´ð´ñ´ò´ó´ô´õ´ö´÷´ø´ù´ú´û´ü´ý´þµ¡µ¢µ£µ¤µ¥µ¦µ§µ¨µ©µªµ«µ¬µ­µ®µ¯µ°µ±µ²µ³µ´µµµ¶µ·µ¸µ¹µºµ»µ¼µ½µ¾µ¿µÀµÁµÂµÃµÄµÅµÆµÇµÈµÉµÊµËµÌµÍµÎµÏµÐµÑµÒµÓµÔµÕµÖµ×µØµÙµÚµÛµÜµÝµÞµßµàµáµâµãµäµåµæµçµèµéµêµëµìµíµîµïµðµñµòµóµôµõµöµ÷µøµùµúµûµüµýµþ¶¡¶¢¶£¶¤¶¥¶¦¶§¶¨¶©¶ª¶«¶¬¶­¶®¶¯¶°¶±¶²¶³¶´¶µ¶¶¶·¶¸¶¹¶º¶»¶¼¶½¶¾¶¿¶À¶Á¶Â¶Ã¶Ä¶Å¶Æ¶Ç¶È¶É¶Ê¶Ë¶Ì¶Í¶Î¶Ï¶Ð¶Ñ¶Ò¶Ó¶Ô¶Õ¶Ö¶×¶Ø¶Ù¶Ú¶Û¶Ü¶Ý¶Þ¶ß¶à¶á¶â¶ã¶ä¶å¶æ¶ç¶è¶é¶ê¶ë¶ì¶í¶î¶ï¶ð¶ñ¶ò¶ó¶ô¶õ¶ö¶÷¶ø¶ù¶ú¶û¶ü¶ý¶þ·¡·¢·£·¤·¥·¦·§·¨·©·ª·«·¬·­·®·¯·°·±·²·³·´·µ·¶···¸·¹·º·»·¼·½·¾·¿·À·Á·Â·Ã·Ä·Å·Æ·Ç·È·É·Ê·Ë·Ì·Í·Î·Ï·Ð·Ñ·Ò·Ó·Ô·Õ·Ö·×·Ø·Ù·Ú·Û·Ü·Ý·Þ·ß·à·á·â·ã·ä·å·æ·ç·è·é·ê·ë·ì·í·î·ï·ð·ñ·ò·ó·ô·õ·ö·÷·ø·ù·ú·û·ü·ý·þ¸¡¸¢¸£¸¤¸¥¸¦¸§¸¨¸©¸ª¸«¸¬¸­¸®¸¯¸°¸±¸²¸³¸´¸µ¸¶¸·¸¸¸¹¸º¸»¸¼¸½¸¾¸¿¸À¸Á¸Â¸Ã¸Ä¸Å¸Æ¸Ç¸È¸É¸Ê¸Ë¸Ì¸Í¸Î¸Ï¸Ð¸Ñ¸Ò¸Ó¸Ô¸Õ¸Ö¸×¸Ø¸Ù¸Ú¸Û¸Ü¸Ý¸Þ¸ß¸à¸á¸â¸ã¸ä¸å¸æ¸ç¸è¸é¸ê¸ë¸ì¸í¸î¸ï¸ð¸ñ¸ò¸ó¸ô¸õ¸ö¸÷¸ø¸ù¸ú¸û¸ü¸ý¸þ¹¡¹¢¹£¹¤¹¥¹¦¹§¹¨¹©¹ª¹«¹¬¹­¹®¹¯¹°¹±¹²¹³¹´¹µ¹¶¹·¹¸¹¹¹º¹»¹¼¹½¹¾¹¿¹À¹Á¹Â¹Ã¹Ä¹Å¹Æ¹Ç¹È¹É¹Ê¹Ë¹Ì¹Í¹Î¹Ï¹Ð¹Ñ¹Ò¹Ó¹Ô¹Õ¹Ö¹×¹Ø¹Ù¹Ú¹Û¹Ü¹Ý¹Þ¹ß¹à¹á¹â¹ã¹ä¹å¹æ¹ç¹è¹é¹ê¹ë¹ì¹í¹î¹ï¹ð¹ñ¹ò¹ó¹ô¹õ¹ö¹÷¹ø¹ù¹ú¹û¹ü¹ý¹þº¡º¢º£º¤º¥º¦º§º¨º©ºªº«º¬º­º®º¯º°º±º²º³º´ºµº¶º·º¸º¹ººº»º¼º½º¾º¿ºÀºÁºÂºÃºÄºÅºÆºÇºÈºÉºÊºËºÌºÍºÎºÏºÐºÑºÒºÓºÔºÕºÖº×ºØºÙºÚºÛºÜºÝºÞºßºàºáºâºãºäºåºæºçºèºéºêºëºìºíºîºïºðºñºòºóºôºõºöº÷ºøºùºúºûºüºýºþ»¡»¢»£»¤»¥»¦»§»¨»©»ª»«»¬»­»®»¯»°»±»²»³»´»µ»¶»·»¸»¹»º»»»¼»½»¾»¿»À»Á»Â»Ã»Ä»Å»Æ»Ç»È»É»Ê»Ë»Ì»Í»Î»Ï»Ð»Ñ»Ò»Ó»Ô»Õ»Ö»×»Ø»Ù»Ú»Û»Ü»Ý»Þ»ß»à»á»â»ã»ä»å»æ»ç»è»é»ê»ë»ì»í»î»ï»ð»ñ»ò»ó»ô»õ»ö»÷»ø»ù»ú»û»ü»ý»þ¼¡¼¢¼£¼¤¼¥¼¦¼§¼¨¼©¼ª¼«¼¬¼­¼®¼¯¼°¼±¼²¼³¼´¼µ¼¶¼·¼¸¼¹¼º¼»¼¼¼½¼¾¼¿¼À¼Á¼Â¼Ã¼Ä¼Å¼Æ¼Ç¼È¼É¼Ê¼Ë¼Ì¼Í¼Î¼Ï¼Ð¼Ñ¼Ò¼Ó¼Ô¼Õ¼Ö¼×¼Ø¼Ù¼Ú¼Û¼Ü¼Ý¼Þ¼ß¼à¼á¼â¼ã¼ä¼å¼æ¼ç¼è¼é¼ê¼ë¼ì¼í¼î¼ï¼ð¼ñ¼ò¼ó¼ô¼õ¼ö¼÷¼ø¼ù¼ú¼û¼ü¼ý¼þ½¡½¢½£½¤½¥½¦½§½¨½©½ª½«½¬½­½®½¯½°½±½²½³½´½µ½¶½·½¸½¹½º½»½¼½½½¾½¿½À½Á½Â½Ã½Ä½Å½Æ½Ç½È½É½Ê½Ë½Ì½Í½Î½Ï½Ð½Ñ½Ò½Ó½Ô½Õ½Ö½×½Ø½Ù½Ú¾¥¾¦¾§¾¨¾©¾ª¾«¾¬¾­¾®¾¯¾°¾±¾²¾³¾´¾µ¾¶¾·¾¸¾¹¾º¾»¾¼¾½¾¾¾¿¾À¾Á¾Â¾Ã¾Ä¾Å¾Æ¾Ç¾È¾É¾Ê¾Ë¾Ì¾Í¾Î¾Ï¾Ð¾Ñ¾Ò¾Ó¾Ô¾Õ¾Ö¾×¾Ø¾Ù¾Ú¾Û¾Ü¾Ý¾Þ¾ß¾à¾á¾â¾ã¾ä¾å¾æ¾ç¾è¾é¾ê¾ë¾ì¾í¾î¾ï¾ð¾ñ¾ò¾ó¾ô½Û½Ü½Ý½Þ½ß½à½á½â½ã½ä½å½æ½ç½è½é½ê½ë½ì½í½î½ï½ð½ñ½ò½ó½ô½õ½ö½÷½ø½ù½ú½û½ü½ý½þ¾¡¾¢¾£¾¤¾õ¾ö¾÷¾ø¾ù¾ú¾û¾ü¾ý¾þ¿¡¿¢¿£¿¤¿¥¿¦¿§¿¨¿©¿ª¿«¿¬¿­¿®¿¯¿°¿±¿²¿³¿´¿µ¿¶¿·¿¸¿¹¿º¿»¿¼¿½¿¾¿¿¿À¿Á¿Â¿Ã¿Ä¿Å¿Æ¿Ç¿È¿É¿Ê¿Ë¿Ì¿Í¿Î¿Ï¿Ð¿Ñ¿Ò¿Ó¿Ô¿Õ¿Ö¿×¿Ø¿Ù¿Ú¿Û¿Ü¿Ý¿Þ¿ß¿à¿á¿â¿ã¿ä¿å¿æ¿ç¿è¿é¿ê¿ë¿ì¿í¿î¿ï¿ð¿ñ¿ò¿ó¿ô¿õ¿ö¿÷¿ø¿ù¿ú¿û¿ü¿ý¿þÀ¡À¢À£À¤À¥À¦À§À¨À©ÀªÀ«À¬À­À®À¯À°À±À²À³À´ÀµÀ¶À·À¸À¹ÀºÀ»À¼À½À¾À¿ÀÀÀÁÀÂÀÃÀÄÀÅÀÆÀÇÀÈÀÉÀÊÀËÀÌÀÍÀÎÀÏÀÐÀÑÀÒÀÓÀÔÀÕÀÖÀ×ÀØÀÙÀÚÀÛÀÜÀÝÀÞÀßÀàÀáÀâÀãÀäÀåÀæÀçÀèÀéÀêÀëÀìÀíÀîÀïÀðÀñÀòÀóÀôÀõÀöÀ÷ÀøÀùÀúÀûÀüÀýÀþÁ¡Á¢Á£Á¤Á¥Á¦Á§Á¨Á©ÁªÁ«Á¬Á­Á®Á¯Á°Á±Á²Á³Á´ÁµÁ¶Á·Á¸Á¹ÁºÁ»Á¼Á½Á¾Á¿ÁÀÁÁÁÂÁÃÁÄÁÅÁÆÁÇÁÈÁÉÁÊÁËÁÌÁÍÁÎÁÏÁÐÁÑÁÒÁÓÁÔÁÕÁÖÁ×ÁØÁÙÁÚÁÛÁÜÁÝÁÞÁßÁàÁáÁâÁãÁäÁåÁæÁçÁèÁéÁêÁëÁìÁíÁîÁïÁðÁñÁòÁóÁôÁõÁöÁ÷ÁøÁùÁúÁûÁüÁýÁþÂ¡Â¢Â£Â¤Â¥Â¦Â§Â¨Â©ÂªÂ«Â¬Â­Â®Â¯Â°Â±Â²Â³Â´ÂµÂ¶Â·Â¸Â¹ÂºÂ»Â¼Â½Â¾Â¿ÂÀÂÁÂÂÂÃÂÄÂÅÂÆÂÇÂÈÂÉÂÊÂËÂÌÂÍÂÎÂÏÂÐÂÑÂÒÂÓÂÔÂÕÂÖÂ×ÂØÂÙÂÚÂÛÂÜÂÝÂÞÂßÂàÂáÂâÂãÂäÂåÂæÂçÂèÂéÂêÂëÂìÂíÂîÂïÂðÂñÂòÂóÂôÂõÂöÂ÷ÂøÂùÂúÂûÂüÂýÂþÃ¡Ã¢Ã£Ã¤Ã¥Ã¦Ã§Ã¨Ã©ÃªÃ«Ã¬Ã­Ã®Ã¯Ã°Ã±Ã²Ã³Ã´ÃµÃ¶Ã·Ã¸Ã¹ÃºÃ»Ã¼Ã½Ã¾Ã¿ÃÀÃÁÃÂÃÃÃÄÃÅÃÆÃÇÃÈÃÉÃÊÃËÃÌÃÍÃÎÃÏÃÐÃÑÃÒÃÓÃÔÃÕÃÖÃ×ÃØÃÙÃÚÃÛÃÜÃÝÃÞÃßÃàÃáÃâÃãÃäÃåÃæÃçÃèÃéÃêÃëÃìÃíÃîÃïÃðÃñÃòÃóÃôÃõÃöÃ÷ÃøÃùÃúÃûÃüÃýÃþÄ¡Ä¢Ä£Ä¤Ä¥Ä¦Ä§Ä¨Ä©ÄªÄ«Ä¬Ä­Ä®Ä¯Ä°Ä±Ä²Ä³Ä´ÄµÄ¶Ä·Ä¸Ä¹ÄºÄ»Ä¼Ä½Ä¾Ä¿ÄÀÄÁÄÂÄÃÄÄÄÅÄÆÄÇÄÈÄÉÄÊÄËÄÌÄÍÄÎÄÏÄÐÄÑÄÒÄÓÄÔÄÕÄÖÄ×ÄØÄÙÄÚÄÛÄÜÄÝÄÞÄßÄàÄáÄâÄãÄäÄåÄæÄçÄèÄéÄêÄëÄìÄíÄîÄïÄðÄñÄòÄóÄôÄõÄöÄ÷ÄøÄùÄúÄûÄüÄýÄþÅ¡Å¢Å£Å¤Å¥Å¦Å§Å¨Å©ÅªÅ«Å¬Å­Å®Å¯Å°Å±Å²Å³Å´ÅµÅ¶Å·Å¸Å¹ÅºÅ»Å¼Å½Å¾Å¿ÅÀÅÁÅÂÅÃÅÄÅÅÅÆÅÇÅÈÅÉÅÊÅËÅÌÅÍÅÎÅÏÅÐÅÑÅÒÅÓÅÔÅÕÅÖÅ×ÅØÅÙÅÚÅÛÅÜÅÝÅÞÅßÅàÅáÅâÅãÅäÅåÅæÅçÅèÅéÅêÅëÅìÅíÅîÅïÅðÅñÅòÅóÅôÅõÅöÅ÷ÅøÅùÅúÅûÅüÅýÅþÆ¡Æ¢Æ£Æ¤Æ¥Æ¦Æ§Æ¨Æ©ÆªÆ«Æ¬Æ­Æ®Æ¯Æ°Æ±Æ²Æ³Æ´ÆµÆ¶Æ·Æ¸Æ¹ÆºÆ»Æ¼Æ½Æ¾Æ¿ÆÀÆÁÆÂÆÃÆÄÆÅÆÆÆÇÆÈÆÉÆÊÆËÆÌÆÍÆÎÆÏÆÐÆÑÆÒÆÓÆÔÆÕÆÖÆ×ÆØÆÙÆÚÆÛÆÜÆÝÆÞÆßÆàÆáÆâÆãÆäÆåÆæÆçÆèÆéÆêÆëÆìÆíÆîÆïÆðÆñÆòÆóÆôÆõÆöÆ÷ÆøÆùÆúÆûÆüÆýÆþÇ¢Ç£Ç¤Ç¥Ç¦Ç§Ç¨Ç©ÇªÇ«Ç¬Ç­Ç®Ç¯Ç°Ç±Ç²Ç³Ç´ÇµÇ¶Ç·Ç¸Ç¹ÇºÇ»Ç¼Ç½Ç¾Ç¿ÇÀÇÁÇÂÇÃÇÄÇÅÇÆÇÇÇÈÇÉÇÊÇËÇÌÇÍÇÎÇÏÇÐÇÑÇÒÇÓÇÔÇÕÇÖÇ×ÇØÇÙÇÚÇÛÇÜÇÝÇÞÇßÇàÇáÇâÇãÇäÇåÇæÇçÇèÇéÇêÇëÇìÇíÇîÇïÇðÇñÇòÇóÇôÇõÇöÇ÷ÇøÇùÇúÇûÇüÇýÇþÈ¡È¢È£È¤È¥È¦È§È¨È©ÈªÈ«È¬È­È®È¯È°È±È²È³È´ÈµÈ¶È·È¸È¹ÈºÈ»È¼È½È¾È¿ÈÀÈÁÈÂÈÃÈÄÈÅÈÆÈÇÈÈÈÉÈÊÈËÈÌÈÍÈÎÈÏÈÐÈÑÈÒÈÓÈÔÈÕÈÖÈ×ÈØÈÙÈÚÈÛÈÜÈÝÈÞÈßÈàÈáÈâÈãÈäÈåÈæÈçÈèÈéÈêÈëÈìÈíÈîÈïÈðÈñÈòÈóÈôÈõÈöÈ÷ÈøÈùÈúÈûÈüÈýÈþÉ¡É¢É£É¤É¥É¦É§É¨É©ÉªÉ«É¬É­É®É¯É°É±É²É³É´ÉµÉ¶É·É¸É¹ÉºÉ»É¼É½É¾É¿ÉÀÉÁÉÂÉÃÉÄÉÅÉÆÉÇÉÈÉÉÉÊÉËÉÌÉÍÉÎÉÏÉÐÉÑÉÒÉÓÉÔÉÕÉÖÉ×ÉØÉÙÉÚÉÛÉÜÉÝÉÞÉßÉàÉáÉâÉãÉäÉåÉæÉçÉèÉéÉêÉëÉìÉíÉîÉïÉðÉñÉòÉóÉôÉõÉöÉ÷ÉøÉùÉúÉûÉüÉýÉþÊ¡Ê¢Ê£Ê¤Ê¥Ê¦Ê§Ê¨Ê©ÊªÊ«Ê¬Ê­Ê®Ê¯Ê°Ê±Ê²Ê³Ê´ÊµÊ¶Ê·Ê¸Ê¹ÊºÊ»Ê¼Ê½Ê¾Ê¿ÊÀÊÁÊÂÊÃÊÄÊÅÊÆÊÇÊÈÊÉÊÊÊËÊÌÊÍÊÎÊÏÊÐÊÑÊÒÊÓÊÔÊÕÊÖÊ×ÊØÊÙÊÚÊÛÊÜÊÝÊÞÊßÊàÊáÊâÊãÊäÊåÊæÊçÊèÊéÊêÊëÊìÊíÊîÊïÊðÊñÊòÊóÊôÊõÊöÊ÷ÊøÊùÊúÊûÊüÊýÊþË¡Ë¢Ë£Ë¤Ë¥Ë¦Ë§Ë¨Ë©ËªË«Ë¬Ë­Ë®Ë¯Ë°Ë±Ë²Ë³Ë´ËµË¶Ë·Ë¸Ë¹ËºË»Ë¼Ë½Ë¾Ë¿ËÀËÁËÂËÃËÄËÅËÆËÇËÈËÉËÊËËËÌËÍËÎËÏËÐËÑËÒËÓËÔËÕËÖË×ËØËÙËÚËÛËÜËÝËÞËßËàËáËâËãËäËåËæËçËèËéËêËëËìËíËîËïËðËñËòËóËôËõËöË÷ËøËùËúËûËüËýËþÌ¡Ì¢Ì£Ì¤Ì¥Ì¦Ì§Ì¨Ì©ÌªÌ«Ì¬Ì­Ì®Ì¯Ì°Ì±Ì²Ì³Ì´ÌµÌ¶Ì·Ì¸Ì¹ÌºÌ»Ì¼Ì½Ì¾Ì¿ÌÀÌÁÌÂÌÃÌÄÌÅÌÆÌÇÌÈÌÉÌÊÌËÌÌÌÍÌÎÌÏÌÐÌÑÌÒÌÓÌÔÌÕÌÖÌ×ÌØÌÙÌÚÌÛÌÜÌÝÌÞÌßÌàÌáÌâÌãÌäÌåÌæÌçÌèÌéÌêÌëÌìÌíÌîÌïÌðÌñÌòÌóÌôÌõÌöÌ÷ÌøÌùÌúÌûÌüÌýÌþÍ¡Í¢Í£Í¤Í¥Í¦Í§Í¨Í©ÍªÍ«Í¬Í­Í®Í¯Í°Í±Í²Í³Í´ÍµÍ¶Í·Í¸Í¹ÍºÍ»Í¼Í½Í¾Í¿ÍÀÍÁÍÂÍÃÍÄÍÅÍÆÍÇÍÈÍÉÍÊÍËÍÌÍÍÍÎÍÏÍÐÍÑÍÒÍÓÍÔÍÕÍÖÍ×ÍØÍÙÍÚÍÛÍÜÍÝÍÞÍßÍàÍáÍâÍãÍäÍåÍæÍçÍèÍéÍêÍëÍìÍíÍîÍïÍðÍñÍòÍóÍôÍõÍöÍ÷ÍøÍùÍúÍûÍüÍýÍþÎ¡Î¢Î£Î¤Î¥Î¦Î§Î¨Î©ÎªÎ«Î¬Î­Î®Î¯Î°Î±Î²Î³Î´ÎµÎ¶Î·Î¸Î¹ÎºÎ»Î¼Î½Î¾Î¿ÎÀÎÁÎÂÎÃÎÄÎÅÎÆÎÇÎÈÎÉÎÊÎËÎÌÎÍÎÎÎÏÎÐÎÑÎÒÎÓÎÔÎÕÎÖÎ×ÎØÎÙÎÚÎÛÎÜÎÝÎÞÎßÎàÎáÎâÎãÎäÎåÎæÎçÎèÎéÎêÎëÎìÎíÎîÎïÎðÎñÎòÎóÎôÎõÎöÎ÷ÎøÎùÎúÎûÎüÎýÎþÏ¡Ï¢Ï£Ï¤Ï¥Ï¦Ï§Ï¨Ï©ÏªÏ«Ï¬Ï­Ï®Ï¯Ï°Ï±Ï²Ï³Ï´ÏµÏ¶Ï·Ï¸Ï¹ÏºÏ»Ï¼Ï½Ï¾Ï¿ÏÀÏÁÏÂÏÃÏÄÏÅÏÆÏÇÏÈÏÉÏÊÏËÏÌÏÍÏÎÏÏÏÐÏÑÏÒÏÓÏÔÏÕÏÖÏ×ÏØÏÙÏÚÏÛÏÜÏÝÏÞÏßÏàÏáÏâÏãÏäÏåÏæÏçÏèÏéÏêÏëÏìÏíÏîÏïÏðÏñÏòÏóÏôÏõÏöÏ÷ÏøÏùÏúÏûÏüÏýÏþÐ¡Ð¢Ð£Ð¤Ð¥Ð¦Ð§Ð¨Ð©ÐªÐ«Ð¬Ð­Ð®Ð¯Ð°Ð±Ð²Ð³Ð´ÐµÐ¶Ð·Ð¸Ð¹ÐºÐ»Ð¼Ð½Ð¾Ð¿ÐÀÐÁÐÂÐÃÐÄÐÅÐÆÐÇÐÈÐÉÐÊÐËÐÌÐÍÐÎÐÏÐÐÐÑÐÒÐÓÐÔÐÕÐÖÐ×ÐØÐÙÐÚÐÛÐÜÐÝÐÞÐßÐàÐáÐâÐãÐäÐåÐæÐçÐèÐéÐêÐëÐìÐíÐîÐïÐðÐñÐòÐóÐôÐõÐöÐ÷ÐøÐùÐúÐûÐüÐýÐþÑ¡Ñ¢Ñ£Ñ¤Ñ¥Ñ¦Ñ§Ñ¨Ñ©ÑªÑ«Ñ¬Ñ­Ñ®Ñ¯Ñ°Ñ±Ñ²Ñ³Ñ´ÑµÑ¶Ñ·Ñ¸Ñ¹ÑºÑ»Ñ¼Ñ½Ñ¾Ñ¿ÑÀÑÁÑÂÑÃÑÄÑÅÑÆÑÇÑÈÑÉÑÊÑËÑÌÑÍÑÎÑÏÑÐÑÑÑÒÑÓÑÔÑÕÑÖÑ×ÑØÑÙÑÚÑÛÑÜÑÝÑÞÑßÑàÑáÑâÑãÑäÑåÑæÑçÑèÑéÑêÑëÑìÑíÑîÑïÑðÑñÑòÑóÑôÑõÑöÑ÷ÑøÑùÑúÑûÑüÑýÑþÒ¡Ò¢Ò£Ò¤Ò¥Ò¦Ò§Ò¨Ò©ÒªÒ«Ò¬Ò­Ò®Ò¯Ò°Ò±Ò²Ò³Ò´ÒµÒ¶Ò·Ò¸Ò¹ÒºÒ»Ò¼Ò½Ò¾Ò¿ÒÀÒÁÒÂÒÃÒÄÒÅÒÆÒÇÒÈÒÉÒÊÒËÒÌÒÍÒÎÒÏÒÐÒÑÒÒÒÓÒÔÒÕÒÖÒ×ÒØÒÙÒÚÒÛÒÜÒÝÒÞÒßÒàÒáÒâÒãÒäÒåÒæÒçÒèÒéÒêÒëÒìÒíÒîÒïÒðÒñÒòÒóÒôÒõÒöÒ÷ÒøÒùÒúÒûÒüÒýÒþÓ¡Ó¢Ó£Ó¤Ó¥Ó¦Ó§Ó¨Ó©ÓªÓ«Ó¬Ó­Ó®Ó¯Ó°Ó±Ó²Ó³Ó´ÓµÓ¶Ó·Ó¸Ó¹ÓºÓ»Ó¼Ó½Ó¾Ó¿ÓÀÓÁÓÂÓÃÓÄÓÅÓÆÓÇÓÈÓÉÓÊÓËÓÌÓÍÓÎÓÏÓÐÓÑÓÒÓÓÓÔÓÕÓÖÓ×ÓØÓÙÓÚÓÛÓÜÓÝÓÞÓßÓàÓáÓâÓãÓäÓåÓæÓçÓèÓéÓêÓëÓìÓíÓîÓïÓðÓñÓòÓóÓôÓõÓöÓ÷ÓøÓùÓúÓûÓüÓýÓþÔ¡Ô¢Ô£Ô¤Ô¥Ô¦Ô§Ô¨Ô©ÔªÔ«Ô¬Ô­Ô®Ô¯Ô°Ô±Ô²Ô³Ô´ÔµÔ¶Ô·Ô¸Ô¹ÔºÔ»Ô¼Ô½Ô¾Ô¿ÔÀÔÁÔÂÔÃÔÄÔÅÔÆÔÇÔÈÔÉÔÊÔËÔÌÔÍÔÎÔÏÔÐÔÑÔÒÔÓÔÔÔÕÔÖÔ×ÔØÔÙÔÚÔÛÔÜÔÝÔÞÔßÔàÔáÔâÔãÔäÔåÔæÔçÔèÔéÔêÔëÔìÔíÔîÔïÔðÔñÔòÔóÔôÔõÔöÔ÷ÔøÔùÔúÔûÔüÔýÔþÕ¡Õ¢Õ£Õ¤Õ¥Õ¦Õ§Õ¨Õ©ÕªÕ«Õ¬Õ­Õ®Õ¯Õ°Õ±Õ²Õ³Õ´ÕµÕ¶Õ·Õ¸Õ¹ÕºÕ»Õ¼Õ½Õ¾Õ¿ÕÀÕÁÕÂÕÃÕÄÕÅÕÆÕÇÕÈÕÉÕÊÕËÕÌÕÍÕÎÕÏÕÐÕÑÕÒÕÓÕÔÕÕÕÖÕ×ÕØÕÙÕÚÕÛÕÜÕÝÕÞÕßÕàÕáÕâÕãÕäÕåÕæÕçÕèÕéÕêÕëÕìÕíÕîÕïÕðÕñÕòÕóÕôÕõÕöÕ÷ÕøÕùÕúÕûÕüÕýÕþÖ¡Ö¢Ö£Ö¤Ö¥Ö¦Ö§Ö¨Ö©ÖªÖ«Ö¬Ö­Ö®Ö¯Ö°Ö±Ö²Ö³Ö´ÖµÖ¶Ö·Ö¸Ö¹ÖºÖ»Ö¼Ö½Ö¾Ö¿ÖÀÖÁÖÂÖÃÖÄÖÅÖÆÖÇÖÈÖÉÖÊÖËÖÌÖÍÖÎÖÏÖÐÖÑÖÒÖÓÖÔÖÕÖÖÖ×ÖØÖÙÖÚÖÛÖÜÖÝÖÞÖßÖàÖáÖâÖãÖäÖåÖæÖçÖèÖéÖêÖëÖìÖíÖîÖïÖðÖñÖòÖóÖôÖõÖöÖ÷ÖøÖùÖúÖûÖüÖýÖþ×¡×¢×£×¤×¥×¦×§×¨×©×ª×«×¬×­×®×¯×°×±×²×³×´×µ×¶×·×¸×¹×º×»×¼×½×¾×¿×À×Á×Â×Ã×Ä×Å×Æ×Ç×È×É×Ê×Ë×Ì×Í×Î×Ï×Ð×Ñ×Ò×Ó×Ô×Õ×Ö×××Ø×Ù×Ú×Û×Ü×Ý×Þ×ß×à×á×â×ã×ä×å×æ×ç×è×é×ê×ë×ì×í×î×ï×ð×ñ×ò×ó×ô×õ×ö×÷×ø×ù";
  private static String TraditionalCnStr = "°¡°¢°£°¤°¥°¦°§°}°©Ì@°«°¬µKÛ°¯°°°±°²°³°´°µ°¶°·°¸°¹°º°»°¼°½°¾°¿Ò\°ÁŠW°Ã°Ä°Å°Æ°Ç°È°É°Ê°Ë°Ì°Í°Î°Ï°Ð°Ñ°Ò‰Î°ÔÁT°Ö°×°Ø°Ù”[°Û”¡°Ý°Þ°ß°à°á°â°ãîC°å°æ°ç°è°é°ê°ëÞk½O°îŽÍ°ð°ñ°ò½‰°ô°õ°öæ^°øÖr°ú°û°ü°ý„ƒ±¡±¢±£±¤ï–Œš±§ˆó±©±ªõU±¬±­±®±¯±°±±Ý…±³Øä^±¶ªN‚ä‘v±º±»±¼±½±¾±¿±À¿‡±Â±Ã±Ä±Å±Æ±Ç±È±É¹P±Ë±Ì±Í±Î®…”À±ÑŽÅ±Ó±Ôé]±Ö±×±Ø±Ù±Ú±Û±Ü±Ý±Þß…¾ŽÙH±â±ã×ƒ±å±æÞqÞp±é˜Ë±ë±ì±í÷M±ï„e°T±ò±óžlžIÙe”P±ø±ù±ú±û±üïž±þ²¡K²£²¤²¥“ÜÀ²¨²©²ª²«ãK²­²®²¯²°²±²²²³²´ñg²¶ÊN²¸Ña²º²»²¼²½²¾²¿²À²Á²Â²Ã²Ä²ÅØ”²Ç²È²É²Ê²Ë²Ì²Í…¢ÐQšˆ‘M‘K NÉnÅ“‚}œæ²Ø²Ù²Ú²Û²Ü²ÝŽú²ß‚ÈƒÔœyŒÓ²ä²å²æ²ç²è²é²ê²ë²ì²í²îÔŒ²ð²ñ²ò”v“½Ïsð’×‹ÀpçP®aêUî²ý²þˆö‡L³£éLƒ”ÄcS³¨•³³ª³«³¬³­ân³¯³°³±³²³³³´Ü‡³¶³·³¸Ø³º³»³¼³½‰m³¿³À³Áê³ÃÒr“Î·Q³Ç³È³É³Ê³Ë³Ì‘Í³ÎÕ\³Ð³ÑòG³Ó³Ô°V³Ö³×³Øßt³ÚñYuýX³Þ³ß³à³á³âŸë³äÐnÐnÏx³çŒ™³é³ê® ÜP³í³î»I³ð¾I³òáh³ô³õ³ö™»N³ùäzër³ü³ý³þµAƒ¦´£´¤Ó|ÌŽ´§´¨´©´ª‚÷´¬´­´®¯´°´±´²êJ„“´µ´¶´·åN´¹´º´»´¼´½´¾¼ƒ´À´Á¾b´Ã´Ä´Å´ÆÞo´È´ÉÔ~´Ë´ÌÙn´ÎÂ”Ê[‡è´ÒÄ…²œ´Ö´×´Ø´ÙÜf´Û¸Z´Ý´Þ´ß´à´á´â´ã´ä´å´æ´ç´è´é´ê´ë´ìåe´îß_´ð´ñ´ò´ó´ô´õ´ö´÷Ž§´ù´úÙJ´ü´ý´þµ¡µ¢“úµ¤†Îà“ÛÄ‘µ©µªµ«‘„µ­ÕQ—µ°®”“õühÊŽ™nµ¶“vµ¸µ¹u¶\Œ§µ½µ¾µ¿µÀ±IµÂµÃµÄµÅŸôµÇµÈµÉµÊà‡µÌµÍµÎµÏ”³µÑµÒœìµÔµÕµÖµ×µØµÙµÚµÛµÜßf¾†îµàµáµâücµäµå‰|ëŠµèµéµêµëµìÕµîµïµðµñµòµóµôµõážÕ{µøµùµúµûµüÕ™¯B¶¡¶¢¶£á”í”¶¦åV¶¨Ó†G–|¶¬¶­¶®„Ó—¶±¶²ƒö¶´¶µ¶¶ôY¶¸¶¹¶º¶»¶¼¶½¶¾ Ùªš×x¶Â¶ÃÙ€¶Ååƒ¶Ç¶È¶É¶Ê¶Ë¶Ìå‘¶Î”à¾„¶Ñƒ¶ê Œ¦¶Õ‡¶×¶ØîD¶Úâg¶Ü¶Ý¶Þ¶ß¶àŠZ¶â¶ã¶ä¶å¶æ¶ç¶è‰™¶ê¶ëùZ¶íî~Óž¶ðº¶ò¶ó¶ô¶õðI¶÷¶øƒº¶ú –ðD¶ý¶þÙE°lÁP·¤·¥·¦éy·¨¬m·ª·«·¬·­·®µ\âC·±·²Ÿ©·´·µ¹ Øœ·¸ïˆ·º·»·¼·½·¾·¿·À·Á·ÂÔL¼·Å·Æ·Ç·Èïw·Ê·ËÕu·Í·ÎU·ÐÙM·Ò·Ó·Ô·Õ·Ö¼Š‰ž·Ù·Ú·ÛŠ^·Ý·Þ‘¼SØS·â—÷·ä·åähïL¯‚·é·êñT¿pÖS·îøP·ð·ñ·ò·óÄw·õ·ö·÷Ý—·ù·ú·û·ü·ý·þ¸¡¸¢¸£¸¤¸¥¸¦“áÝo¸©¸ª¸«¸¬¸­¸®¸¯¸°¸±¸²ÙxÑ}¸µ¸¶¸·¸¸¸¹Ø“¸»Ó‡¸½‹D¿`¸À¸Á¸ÂÔ“¸Ä¸Åâ}Éw¸ÈŽÖ¸Ê¸Ë¸Ì¸Í¸ÎÚs¸Ð¶’¸ÒÚMŒù„‚ä“¸×¸Ø¾V¸Û¸Ü¸Ý¸Þ¸ß¸à¸á¸â¸ãæ€¸å¸æ¸ç¸è”R¸êø¸ì¸í¸î¸ï¸ð¸ñ¸òéw¸ôãt‚€¸÷½o¸ù¸ú¸û¸ü¸ý¸þ¹¡¹¢¹£¹¤¹¥¹¦¹§ý¹©¹ª¹«Œm¹­ì–¹¯¹°Ø•¹²ã^¹´œÏ¹¶¹·¹¸˜‹Ù‰ò¹¼¹½¹¾¹¿¹À¹Á¹Â¹Ã¹Ä¹ÅÐM¹Ç¹È¹É¹Êî™¹Ì¹Í¹Î¹Ï„Ž¹Ñ’ì¹Ó¹Ô¹Õ¹Ö¹×êP¹Ù¹ÚÓ^¹Üð^¹Þ‘T¹àØž¹âV¹ä¹åÒŽ¹çÎùšwý”é|Ü‰¹íÔŽ¹ï¹ð™™¹òÙF„£ÝL¹÷å¹ù‡ø¹û¹üß^¹þº¡º¢º£º¤º¥º¦ñ”º¨º©ºªínº¬º­º®º¯º°º±º²º³º´ºµº¶º·º¸º¹hº»º¼º½º¾º¿ºÀºÁºÂºÃºÄÌ–ºÆºÇºÈºÉºÊºËºÌºÍºÎºÏºÐºÑéuºÓºÔºÕºÖúQÙRºÙºÚºÛºÜºÝºÞºßºà™MºâºãÞZºåºæºçø™ºéºêºë¼tºíºîºïºðºñºòááºôºõºöº÷‰Øºùºúºûºüºýºþ»¡»¢»£×o»¥œû‘ô»¨‡WÈA»«»¬®‹„»¯Ô’»±»²‘Ñ»´‰Äšg­h»¸ß€¾“Q»¼†¾¯ˆ»¿Ÿ¨œo»Â»Ã»Ä»ÅüS»Ç»È»É»Ê»Ë»Ì»Í»Î»Ï»ÐÖe»Ò“]Ýx»Õ»Ö»×»Øš§»Ú»Û»Ü»Ý»ÞÙV·x•þ Z…RÖMÕdÀLÈ»è»é»êœ†»ì»í»îâ·»ð«@»ò»ó»ôØ›µœ“ô»ø»ù™C»û»ü·e»þ¼¡ð‡ÛE¼¤×Iëu¼§¿ƒ¾ƒ¼ª˜O¼¬Ý‹¼®¼¯¼°¼±¼²¼³¼´¼µ¼‰”DŽ×¼¹¼ºËE¼¼¼½¼¾¼¿¼À„©¼Âú¼Ä¼ÅÓ‹Ó›¼È¼ÉëH¼ËÀ^¼o¼Î¼ÏŠA¼Ñ¼Ò¼ÓÇvîaÙZ¼×â›¼Ù¼Úƒr¼Üñ{¼Þšž±OˆÔ¼â¹{ég¼å¼æ¼çÆD¼é¾}ÀO™z¼í‰Aû|’þ“ìº†ƒ€¼ôœpË]™‘èbÛ`ÙvÒŠæI¼ý¼þ½¡Åž„¦ðTužR¾½¨½©½ªŒ¢{½­½®ÊY˜ªª„Öv½³áu½µ½¶½·½¸½¹Äz½»½¼²òœ‹É½À”‡ãq³CƒeÄ_½Æ½ÇïœÀU½g½Ë½Ì½ÍÞIÝ^½Ð½Ñ½Ò½Ó½Ô½Õ½ÖëA½Ø½Ù¹Ço¾¦¾§öL¾©ó@¾«¾¬½›¾®¾¯¾°îiìo¾³¾´çR½¯d¾¸¾¹¸‚œQ¾¼¾½¾¾¾¿¼m¾Á¾Â¾Ã¾Ä¾Å¾ÆŽý¾ÈÅf¾Ê¾Ë¾Ì¾Í¾Î¾Ï¾Ð¾Ñ¾Ò¾Óñx¾Õ¾Ö¾×¾ØÅe¾Ú¾Û¾Ü“þ¾Þ¾ß¾à¾áä¾ã¾ä‘Ö¾æ„¡¾èùN¾ê¾ë¾ì¾í½¾ï¾ð¾ñ¾ò¾ó¾ô½Û‚Ü½Ý½Þ½ß½Y½â½ã½ä½å½æ½ç½è½é½êÕ]ŒÃ½í½î½ï½ð½ñ½ò½ó¾oå\ƒHÖ”ßM½ù•x½û½ü a½þ±M„ÅÇG¾¤ÓX›QÔE½^¾ù¾úâxÜŠ¾ý¾þ¿¡¿¢¿£¿¤òE¿¦¿§¿¨¿©é_¿«¿¬„P¿®¿¯¿°¿±¿²¿³¿´¿µ¿¶¿·¿¸¿¹¿º¿»¿¼¿½¿¾¿¿¿À¿Á¿Â¿Ã¿Äîw¿Æš¤¿È¿É¿Ê¿Ë¿Ì¿ÍÕn¿Ï¿Ð‰¨‘©¿Ó¿Ô¿Õ¿Ö¿×¿Ø“¸¿Ú¿Û¿Ü¿Ý¿Þ¿ß¿à¿áŽìÑÕF¿å¿æ¿ç¿è‰K¿êƒ~¿ìŒ’¿î¿ï¿ð¿ñ¿òµV¿ô•ç›rÌ¿øŽh¸Q¿û¿ü¿ý¿þðÀ¢¢À¤À¥À¦À§À¨”UÀªéŸÀ¬À­À®ÏžÅDÀ±À²ÈRíÙ‡Ë{À·™Ú”r»@ê@Ìmž‘×Ž”ˆÓ[‘ÐÀ| €žEÀÅÀÆÀÇÀÈÀÉÀÊÀË“Æ„ÚÀÎÀÏÀÐÀÑÀÒÀÓ³ÀÕ˜·À×èDÀÙÀÚÀÛÀÜ‰¾ÀÞÀßîœIÀâÀãÀäÀåÀæÀçÀè»hÀêëxÀìÀíÀîÑYõŽ¶YÀòÀóÀôÀõû…–„îµ[•ÑÀûÀüÀýÀþÁ¡Á¢Á£žrë`Á¦Á§Á¨‚zÂ“ÉßBç Á®‘ziºŸ”¿Ä˜æœ‘ÙŸ’¾š¼Z›öÁºÁ»Á¼ƒÉÝvÁ¿ÁÀÁÁÕÁÃÁÄÁÅ¯ŸÁÇÁÈß|ÁÊÁËÁÌç‚ÁÎÁÏÁÐÁÑÁÒÁÓ«CÁÕÁÖÁ×ÁØÅRà÷[ÁÜ„CÙUÁßÁàÁáÁâÁãýgâÁæÁçœRì`ÁêŽXîIÁíÁîÁïÁðÁñÁòðsÁô„¢ÁöÁ÷ÁøÁùýˆÃ@‡µ»\ÁþÂ¡‰Å”në]˜ÇŠä“§ºtÂ©ÂªÌJ±RïB] t“ïûuÌ”ô”Â´ÂµÂ¶Â·ÙTÂ¹Âºµ“ä›ê‘Â¾óH…ÎäX‚HÂÃÂÄŒÒ¿|‘]ÂÈÂÉÂÊžV¾GŽn”Œ\ž´ÂÑyÂÓÂÔ’àÝ†‚öœS¾]Õ“Ì}ÂÝÁ_ß‰èŒ»jò…ÂãÂäÂåñ˜½j‹ŒÂé¬”´aÎ›ñRÁRÂï†áÂñÙIûœÙuß~Ã}²mðzÐUMÂûÂüÂýÂþÖ™Ã¢Ã£Ã¤Ã¥Ã¦Ã§ØˆÃ©å^Ã«Ã¬ãTÃ®Ã¯Ã°Ã±Ã²ÙQüNÃµÃ¶Ã·Ã¸üqÃº›]Ã¼Ã½æVÃ¿ÃÀÃÁÃÂÃÃÃÄéTž‚ƒÃÈÃÉÃÊÃËåiÃÍ‰ôÃÏÃÐÃÑÃÒÃÓÃÔÖi›Ã×ÃØÒ’ÃÚÃÛÃÜƒçÃÞÃß¾dÃáÃâÃãÃä¾’ÃæÃçÃèÃéÃêÃëÃìRÃîÃïœçÃñÃòÃóÃô‘‘é}Ã÷ÃøøQã‘ÃûÃüÖ‡ÃþÄ¡Ä¢Ä£Ä¤Ä¥Ä¦Ä§Ä¨Ä©ÄªÄ«Ä¬Ä­Ä®Ä¯Ä°Ö\Ä²Ä³Ä´Äµ®€Ä·Ä¸Ä¹ÄºÄ»Ä¼Ä½Ä¾Ä¿ÄÀÄÁÄÂÄÃÄÄÄÅâcÄÇÄÈ¼{ÄÊÄËÄÌÄÍÄÎÄÏÄÐëyÄÒ“ÏÄXÀô[Ä×ÄØðHƒÈÄÛÄÜÄÝÄÞÄßÄàÄá”MÄãÄäÄÄæÄçÄèÄéÄêÄë”f“ÓÄîÄïá„øBÄòÄóÂ™Äõýmè‡æ‡ÄùÄú™ŽªŸÄýå¸”QôÅ£Å¤âo¼~Ä“âÞrÅªÅ«Å¬Å­Å®Å¯Å°¯‘Å²Å³Å´ÖZÅ¶šWútšªÅº‡IÅ¼aÅ¾Å¿ÅÀÅÁÅÂÅÃÅÄÅÅÅÆÅÇÅÈÅÉÅÊÅË±PÅÍÅÎÅÏÅÐÅÑÅÒý‹ÅÔÅÕÅÖ’ÅØÅÙÅÚÅÛÅÜÅÝÅÞÅßÅàÅáÙrÅãÅäÅåÅæ‡ŠÅèÅéÅêÅëÅìÅíÅîÅïÅðÅñÅòÅóùiÅõÅöÅ÷ÅøÅùÅúÅûÅüÅýÅþÆ¡Æ¢Æ£Æ¤Æ¥Æ¦Æ§Æ¨Æ©ÆªÆ«Æ¬ò_ïhÆ¯Æ°Æ±Æ²Æ³Æ´îlØšÆ·Æ¸Æ¹ÆºÌOÆ¼Æ½‘{Æ¿ÔuÆÁÆÂŠîHÆÅÆÆÆÇÆÈÆÉÆÊ“ääƒWÆÎÆÏÆÐÆÑÆÒ˜ãÆÔÆÕÆÖ×VÆØÆÙÆÚÆÛ—«ÆÝÆÞÆßœDÆáÆâÆãÆäÆåÆæÆçÆèÆéÄšýRÆìÆíÆîòTÆðØMÆòÆó†¢ÆõÆöÆ÷šâÆù—‰ÆûÆüÓ™ÆþÇ¢ ¿’LâTãUÇ§ßwºžÇªÖtÇ¬Ç­åXãQÇ°“Ç²œ\×l‰qÇ¶Ç·Ç¸˜Œ†ÜÇ»Ç¼ ËNŠ“ŒÇÁæ@ÇÃÇÄ˜òÇÆ†ÌƒSÇÉÇÊÇËÂNÇÍÇÎ¸[ÇÐÇÑÇÒÇÓ¸`šJÇÖÓHÇØÇÙÇÚÇÛÇÜÇÝŒ‹ÇßÇàÝpšäƒAÇäÇåÇæÇçÇèÇéí•Õˆ‘c­‚¸FÇïÇðÇñÇòÇóÇôÇõÇöÚ……^ÇùÇúÜ|ÇüòŒÇþÈ¡È¢ýxÈ¤È¥È¦ïE™àÈ©ÈªÈ«È¬È­È®È¯„ñÈ±È²È³…sùoÈ¶´_È¸È¹ÈºÈ»È¼È½È¾È¿ÈÀÈÁÈÂ×Œðˆ”_À@ÈÇŸáÈÉÈÊÈËÈÌígÈÎÕJÈÐÈÑ¼xÈÓÈÔÈÕÈÖÈ×ÈØ˜sÈÚÈÛÈÜÈÝ½qÈßÈàÈáÈâÈãÈäÈåÈæÈçÈèÈéÈêÈëÈìÜ›ÈîÈïÈðäJéc™ÈôÈõÈöž¢Ë_ÈùöwÈûÙÈýÈý‚ãÉ¢É£É¤†ÊÉ¦ò}’ßÉ©ÉªÉ«­É­É®É¯É°š¢É²É³¼†ÉµÉ¶É·ºY•ñÉºÉ»É¼É½„hÉ¿ÉÀéWê„ÉÃÙ ÉÅÉÆÉÇÉÈ¿˜ÉÊ‚ûÉÌÙpÉÎÉÏÉÐÉÑÉÒÉÓÉÔŸýÉÖÉ×ÉØÉÙÉÚÉÛ½BÉÝÙdÉßÉàÉáÉâ”zÉä‘ØÉæÉçÔOÉéÉêÉëÉìÉíÉîÉï¼ÉñÉòŒ‹ðÉõÄIÉ÷BÂ•ÉúÉûÉüÉýÀKÊ¡Ê¢Ê£„ÙÂ}ŽŸÊ§ª{Ê©ñÔŠŒÆÊ­Ê®Ê¯Ê°•rÊ²Ê³ÎgŒ×RÊ·Ê¸Ê¹Êºñ‚Ê¼Ê½Ê¾Ê¿ÊÀÊÁÊÂÊÃÊÄÊÅ„ÝÊÇÊÈÊÉßmÊËÊÌáŒï—ÊÏÊÐÊÑÊÒÒ•Ô‡ÊÕÊÖÊ×ÊØ‰ÛÊÚÊÛÊÜÊÝ«FÊß˜ÐÊáÊâÊãÝ”ÊåÊæÊçÊè•øÚHÊëÊìÊíÊîÊïÊðÊñÊòÊóŒÙÐgÊö˜äÊøÊùØQÊûÊü”µÊþË¡Ë¢Ë£Ë¤Ë¥Ë¦Ž›Ë¨Ë©ËªëpË¬ÕlË®Ë¯¶Ë±Ë²í˜Ë´Õf´TË· qË¹ËºË»Ë¼Ë½Ë¾½zËÀËÁËÂËÃËÄËÅËÆï•ËÈËÉÂ–‘ZížËÍËÎÔAÕbËÑËÒ”\ËÔÌKËÖË×ËØËÙËÚËÛËÜËÝËÞÔVÃCËáËâËãëmËåëS½—ËèËéšqËëËìËíËîŒO“p¹SËòËóËô¿s¬Ë÷æiËùËúËûËüËýËþ«H“éÌ£Ì¤Ì¥Ì¦Ì§Ì¨Ì©ÌªÌ«‘BÌ­Ì®”‚Ø°cž©‰¯Ì´ÌµÌ¶×TÕ„Ì¹ÌºÌ»Ì¼Ì½šUÌ¿œ«ÌÁÌÂÌÃÌÄÌÅÌÆÌÇÌÈÌÉÌÊÌË CÌÍýÌÏ½dÌÑÌÒÌÓÌÔÌÕÓ‘Ì×ÌØÌÙòvÌÛÖ`ÌÝÌÞÌßäRÌáî}ÌãÌäówÌæÌçÌèÌéÌêŒÏÌìÌíÌîÌïÌðÌñÌòÌóÌô—lÌöÌ÷ÌøÙNèFÌûdÂ ŸNÍ¡Í¢Í£Í¤Í¥Í¦Í§Í¨Í©ÍªÍ«Í¬ã~Í®Í¯Í°Í±Í²½yÍ´ÍµÍ¶î^Í¸Í¹¶dÍ»ˆDÍ½Í¾‰TÍÀÍÁÍÂÍÃÍÄˆFÍÆîjÍÈÍ‘ÍÊÍËÍÌÍÍÍÎÍÏÍÐÃ“ørÍÓñWñ„™EÍ×ÍØÍÙÍÚÍÛÍÜ¸DÍÞÍßÒmÍáÍâÍãž³ÍæîBÍèÍéÍêÍëÍìÍíÍîÍïÍðÍñÈfÍóÍôÍõÍöÍ÷¾WÍùÍúÍûÍüÍýÍþÎ¡Î¢Î£ífß`Î¦‡úÎ¨Î©žéžH¾SÈ”Î®Î¯‚¥‚ÎÎ²¾•Î´ÎµÎ¶Î·Î¸Î¹ÎºÎ»Î¼Ö^Î¾Î¿ÐlÎÁœØÎÃÎÄÂ„¼yÎÇ·€ÎÉ†–ÎËÎÌ®Y“ëÎœu¸CÎÒÎÓÅPÎÕÎÖÎ×†èæužõ›@Õ_ÎÝŸoÊÎàÎá…ÇÎãÎäÎåÎæÎçÎèÎéÎê‰]ÎììFÎîÎïÎð„ÕÎòÕ`ÎôÎõÎöÎ÷ÎøÎùÎúÎûÎüåa ÞÏ¡Ï¢Ï£Ï¤Ï¥Ï¦Ï§Ï¨Ï©ÏªÏ«Ï¬Ï­ÒuÏ¯Á•Ï±Ï²ãŠÏ´ÏµÏ¶‘ò¼šÏ¹ÎrÏ»Ï¼Ý Ï¾{‚bªMÏÂBÏÄ‡˜ÏÆåvÏÈÏÉõrÀwûyÙtã•ÏÏéeÏÑÏÒÏÓï@ëU¬F«I¿hÏÙðWÁw‘—ÏÝÏÞ¾€ÏàŽûè‚ÏãÏäÏåÏæàlÏèÏéÔ”Ïëí‘Ïíí—ÏïÏðÏñÏòÏóÊ’ÏõÏöÏ÷Ïø‡ÌäNÏûÏüÏý•ÔÐ¡Ð¢Ð£Ð¤‡[Ð¦Ð§Ð¨Ð©ÐªÏÐ¬…f’¶”yÐ°Ð±Ã{ÖCŒ‘ÐµÐ¶Ð·Ð¸Ð¹žaÖxÐ¼Ð½Ð¾ä\ÐÀÐÁÐÂÐÃÐÄÐÅá…ÐÇÐÈÐÉÐÊÅdÐÌÐÍÐÎÐÏÐÐÐÑÐÒÐÓÐÔÐÕÐÖÐ×ÐØÐÙ›°ÐÛÐÜÐÝÐÞÐßÐàÐáçnÐãÐäÀCÐæÐçÐèÌ“‡uíšÐìÔSÐîÐï”¢ÐñÐòÐóÐôÐõÐö¾wÀmÜŽÐúÐû‘ÒÐýÐþßx°_Ñ£½kÑ¥Ñ¦ŒWÑ¨Ñ©Ñª„ìÑ¬Ñ­Ñ®ÔƒŒ¤ñZÑ²Ñ³Ñ´Ó–ÓßdÑ¸‰ºÑºøfø†Ñ½Ñ¾Ñ¿ÑÀÑÁÑÂÑÃÑÄÑÅ†¡†Ó ÑÉÑÊéŽŸŸÑÍû}‡ÀÑÐÑÑÑÒÑÓÑÔîéÑ×ÑØÑÙÑÚÑÛÑÜÑÝØWÑßÑà…’³ŽÑãÑä©ÑæÑçÖVòžÑêÑëø„Ñí—î“PÑð¯ƒÑòÑóê–ÑõÑö°WðB˜ÓÑúÑûÑüÑý¬Ž“uˆòßb¸GÖ{Ò¦Ò§Ò¨ËŽÒªÒ«Ò¬Ò­Ò® ”Ò°Ò±Ò²í“Ò´˜IÈ~Ò·Ò¸Ò¹ÒºÒ»Ò¼átÒ¾ãžÒÀÒÁÒÂîUÒÄßzÒÆƒxÒÈÒÉÒÊÒËÒÌÒÍÒÎÏÒÐÒÑÒÒÒÓÒÔË‡ÒÖÒ×ÒØÒÙƒ|ÒÛÒÜÒÝÒÞÒßÒàÒáÒâÒã‘›ÁxÒæÒçÔ„×hÕx×g®ÒíÒîÀ[ÒðÊaÒòÒóÒôêŽÒöÒ÷ãyÒùÒúï‹ÒüÒýë[Ó¡Ó¢™Ñ‹ëú—‘ªÀt¬“Îž IŸÉÏ‰Ó­ÚAÓ¯Ó°·fÓ²Ó³†Ñ“í‚òÓ·°bÓ¹ÓºÛxÓ¼ÔÓ¾œ¥ÓÀÓÁÓÂÓÃÓÄƒžÓÆ‘nÓÈÓÉà]â™ªqÓÍÓÎÓÏÓÐÓÑÓÒÓÓÓÔÕTÓÖÓ×ÓØÓÙÓÚÓÛÓÜÓÝÓÞÝ›ÓàÓáÓâô~ÓäÓåOÓçÓèŠÊÓêÅcŽZÓíÓîÕZÓðÓñÓòÓóÓô»nÓöÓ÷Óø¶RÓúÓûªzÓý×uÔ¡Ô¢Ô£îAÔ¥ñSøxœYÔ©ÔªÔ«Ô¬Ô­Ô®Þ@ˆ@†TˆAÔ³Ô´¾‰ßhÔ·îŠÔ¹ÔºÔ»¼sÔ½ÜSè€Ž[»›ÔÂ‚é†ÔÅë…ày„òëEÔÊß\ÌNáj•žíÔÐÔÑÔÒësÔÔÔÕžÄÔ×ÝdÔÙÔÚÔÛ”€•ºÙÚEóvÔáÔâÔãèÔå——ÔçÔèÔéÔêÔëÔìÔíÔîÔïØŸ“ñ„tÉÙ\ÔõÔöÔ÷ÔøÙ›¼™ÔûÔü„žÜˆåŽélÕ£–ÅÕ¥Õ¦Õ§Õ¨ÔpÕªýSÕ¬Õ­‚ùÕ¯Õ°šÖÕ²Õ³Õ´±K”ØÝšäÕ¹Õº—£Õ¼‘ðÕ¾Õ¿¾`ÕÁÕÂÕÃÕÄˆÕÆqÕÈÕÉŽ¤Ù~ÕÌÃ›ÕÎÕÏÕÐÕÑÕÒÕÓÚwÕÕÕÖÕ×ÕØÕÙÕÚÕÛÕÜÏUÞHÕßæNÕáß@ÕãÕäÕåÕæÕçÕèÕéØ‘á˜‚ÉÕíÕîÔ\ÕðÕñæ‚ê‡Õô’ê± Õ÷ªb ŽÕúÕûÕüÕýÕþŽ¬Ö¢à×CÖ¥Ö¦Ö§Ö¨Ö©ÖªÖ«Ö¬Ö­Ö®¿—ÂšÖ±Ö²Ö³ˆÌÖµÖ¶Ö·Ö¸Ö¹ÖºÖ»Ö¼¼ˆÖ¾“´”SÖÁÖÂÖÃŽÃÖÅÖÆÖÇÖÈÖÉÙ|ÖËÖÌœþÖÎÖÏÖÐÖÑÖÒçŠÖÔ½K·NÄ[ÖØÖÙ±ŠÖÛÖÜÖÝÖÞÖaÖàÝSÖâÖãÖä°™Öæ•ƒóEÖéÖêÖëÖìØiÖTÕDÖðÖñ TÖóÖô²š‡ÚÖ÷ÖøÖùÖúÖûÙAèTºB×¡×¢×£ñv×¥×¦×§Œ£´uÞD×«Ù×­˜¶ÇfÑbŠy×²‰Ñ î×µåF×·Ù˜‰‹¾YÕ×¼×½×¾×¿×À×Á×Â×Ã×ÄÖø×ÆáÆ×ÉÙY×Ë×Ì×Í×Î×Ï×Ð×Ñ×Ò×Ó×Ôn×Ö×××ØÛ™×Ú¾C¿‚¿vàu×ß×à×á×â×ã×ä×å×æÔ{×è½Mè×ë×ì×í×î×ï×ð×ñ×ò×ó×ô×õ×ö×÷×ø×ù";

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
    String tmp = "£²£´£µÈf£²´øµÄ£Á£Æ£Ä£Å£Ã£Ä£Ò£Ç£µ£¶£¬°¡ £Á£Â£Ã£Ä¡¡£÷£ø£ù£úµç¡¡Èý¶þ£¬¡££¿¡¢ÈýËÄ";
    System.out.println(DBC2SBC(tmp));
    System.out.println(SimplifyToTraditional("ÖÐÎÄ·±Ìå±ä»»²âÊÔ"));
    System.out.println(TraditionalToSimplify("ÙYÓ  ówÓý  ŠÊ˜·  ¿Æ¼¼  ˜Ç±P "));
  }
}