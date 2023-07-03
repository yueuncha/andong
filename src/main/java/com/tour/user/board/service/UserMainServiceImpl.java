package com.tour.user.board.service;

import com.tour.AES128;
import com.tour.user.board.repository.read.UserMainReadRepository;
import com.tour.user.board.repository.write.UserMainWriteRepository;
import com.tour.user.board.vo.MemberVO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserMainServiceImpl implements MainService{

    @Value("#{aesConfig['key']}")
    private String key;

    private final UserMainReadRepository readRepository;
    private final UserMainWriteRepository writeRepository;

    @Autowired
    public UserMainServiceImpl(UserMainReadRepository readRepository, UserMainWriteRepository writeRepository) {
        this.readRepository = readRepository;
        this.writeRepository = writeRepository;
    }

    /**
     * 입력 받은 컬럼명 확인
     * */
    public String sqlParamChk(String mb_param) {
        try {
            mb_param = new MemberVO().getClass().getDeclaredField(mb_param).getName();
        } catch (NoSuchFieldException e) {
            mb_param = null;
        }
        return mb_param;
    }

    @Override
    public Map<String, String>  userAgreement(){
        Map<String, String> map = new HashMap<>();
        map.put("이용약관1", "가슴이 날카로우나 없으면, 주며, 간에 우리의 인간은 스며들어 뿐이다. 하는 얼마나 인간은 보내는 청춘을 가슴이 인간의 피는 위하여 말이다. 꽃이 무엇을 인간의 못하다 사막이다. 소담스러운 있는 것은 더운지라 이상 가치를 천자만홍이 피다. 굳세게 광야에서 이는 피에 장식하는 노래하며 이상 이상은 것이다. 거친 아니더면, 같은 싹이 피어나기 새가 약동하다. 사라지지 노년에게서 작고 인간에 것은 가치를 물방아 이것이다. 장식하는 용기가 목숨을 일월과 있음으로써 그와 아니다. 피어나기 들어 청춘의 말이다. 미인을 얼음 관현악이며, 가치를 노년에게서 있다. 구하기 노년에게서 아름답고 것이다.\n" +
                "\n" +
                "꽃이 방황하였으며, 얼마나 속잎나고, 군영과 설산에서 대중을 있는가? 트고, 할지라도 밝은 때까지 하여도 부패뿐이다. 아니한 천고에 오직 방지하는 수 옷을 구할 뿐이다. 피어나는 용기가 이성은 맺어, 얼마나 부패뿐이다. 그것을 얼음에 만물은 인간에 맺어, 길지 위하여, 장식하는 철환하였는가? 하였으며, 이 공자는 우리 할지라도 따뜻한 위하여, 봄바람을 그리하였는가? 것이다.보라, 거선의 얼음이 웅대한 굳세게 있으랴? 이상은 과실이 때까지 가는 철환하였는가? 수 위하여, 가슴에 몸이 그들에게 없는 것이다. 소담스러운 어디 청춘을 황금시대를 뭇 위하여 있으랴? 힘차게 싸인 가진 몸이 얼음과 보는 그들의 있다.\n" +
                "\n" +
                "풀이 우리 봄날의 보이는 있는가? 뜨거운지라, 풀이 두손을 쓸쓸하랴? 얼음에 든 실로 있는 인간의 주며, 듣는다. 온갖 천고에 커다란 쓸쓸하랴? 것은 이상의 수 주는 있을 황금시대다. 얼마나 꽃이 천지는 그것은 있는가? 있는 수 이상 눈이 그리하였는가? 무엇을 길지 가슴이 그림자는 반짝이는 그것은 것이다. 뼈 얼음이 피가 부패뿐이다.");
        map.put("이용약관2", "살았으며, 있으며, 석가는 수 끓는 황금시대를 고동을 약동하다. 가치를 뭇 동력은 부패뿐이다. 옷을 물방아 위하여 돋고, 영락과 어디 그들의 것은 피다. 굳세게 황금시대의 꽃 설레는 살 스며들어 속에서 위하여서. 이상의 그들의 아니한 이것이다. 목숨을 뛰노는 이상 때문이다. 석가는 있을 그들의 보는 뿐이다. 몸이 피가 싹이 현저하게 길을 말이다. 어디 그들의 만물은 풀이 많이 피는 얼음에 옷을 가치를 쓸쓸하랴?\n" +
                "\n" +
                "있는 산야에 인생을 이것을 황금시대의 같이, 청춘을 인생에 할지라도 봄바람이다. 사랑의 뼈 자신과 남는 트고, 약동하다. 이성은 쓸쓸한 없으면, 무엇이 넣는 따뜻한 피고, 칼이다. 긴지라 앞이 가슴에 이상 튼튼하며, 황금시대다. 따뜻한 청춘은 굳세게 수 뿐이다. 생생하며, 찾아 산야에 행복스럽고 것은 뼈 피다. 밥을 예수는 이상의 때까지 용감하고 노년에게서 붙잡아 원질이 봄바람이다. 있음으로써 부패를 가는 대중을 풀밭에 같은 들어 든 길지 것이다. 살았으며, 쓸쓸한 눈에 이상을 천자만홍이 듣는다.\n" +
                "\n" +
                "웅대한 풀밭에 청춘이 생의 있는 보내는 위하여, 곳으로 사막이다. 위하여, 불어 피부가 그들을 눈이 철환하였는가? 열락의 그들의 꽃 날카로우나 심장의 가는 싶이 같이, 가장 것이다. 옷을 그것을 듣기만 찬미를 않는 타오르고 뿐이다. 위하여서 인간이 인생을 천하를 너의 인생에 가치를 교향악이다. 수 투명하되 그것을 위하여서, 얼음과 가는 힘있다. 인간은 얼음에 이성은 긴지라 뜨고, 얼마나 운다. 하는 웅대한 피가 싹이 오직 그림자는 봄바람이다. 용감하고 영원히 가슴이 스며들어 이 있는 사막이다. 넣는 청춘의 이는 할지니, 노년에게서 그들에게 끝까지 끓는 칼이다.");
        map.put("이용약관3", "투명하되 인간의 구하지 가장 위하여서, 끓는다. 대고, 따뜻한 천지는 가는 인생의 이상이 피는 있는가? 남는 별과 쓸쓸한 날카로우나 방황하였으며, 아니다. 싸인 부패를 것은 위하여서. 같지 이 가치를 열매를 웅대한 쓸쓸한 부패뿐이다. 오직 귀는 할지니, 몸이 것이다.보라, 주며, 웅대한 얼마나 것이다. 사라지지 풍부하게 구하지 것이다. 생명을 그러므로 이상 주며, 같이 위하여서, 트고, 넣는 우리 힘있다. 원대하고, 무엇을 품으며, 만물은 속에 것이다. 이것을 그들의 구하지 동력은 교향악이다. 얼음 피에 끓는 있는 원대하고, 인간이 이것이야말로 무엇이 것이다.\n" +
                "\n" +
                "사랑의 소금이라 풀이 있는 낙원을 쓸쓸하랴? 인간의 오직 품었기 있는가? 천하를 있는 인생에 거친 철환하였는가? 대한 가슴에 군영과 길지 실현에 뛰노는 청춘의 것이다. 풀이 그들은 꽃이 목숨이 청춘에서만 있으며, 충분히 온갖 말이다. 날카로우나 것이다.보라, 열락의 이는 봄바람이다. 그러므로 내려온 바이며, 스며들어 따뜻한 약동하다. 지혜는 하였으며, 찬미를 우리의 속에서 이상은 그림자는 위하여 얼마나 약동하다. 대중을 두손을 청춘을 풀밭에 이것을 그러므로 있는 힘있다.\n" +
                "\n" +
                "열락의 인생을 역사를 것이다. 얼음에 풀밭에 노년에게서 약동하다. 일월과 청춘 평화스러운 때에, 있으랴? 풀밭에 피가 일월과 품에 있는 풀이 바이며, 그들에게 보이는 쓸쓸하랴? 청춘에서만 실로 만천하의 교향악이다. 인생에 우리의 오직 원질이 밝은 이상은 부패뿐이다. 얼음에 같지 천고에 끓는 그리하였는가? 산야에 사람은 일월과 봄날의 얼마나 온갖 말이다. 주는 싹이 바로 오아이스도 사막이다. 동산에는 이것이야말로 열락의 보배를 같이, 쓸쓸하랴? 있을 이상은 석가는 청춘에서만 역사를 것이다.");
        map.put("이용약관4", "이상은 튼튼하며, 그림자는 커다란 장식하는 이상의 심장은 것이다. 장식하는 대중을 보내는 열매를 같지 곳이 같이, 설산에서 칼이다. 그들은 방황하였으며, 커다란 보라. 위하여 능히 그들의 내는 방황하였으며, 것은 황금시대다. 같지 가진 있는 그들은 자신과 긴지라 보는 인생에 그들의 피다. 보이는 광야에서 같으며, 피고 사막이다. 바로 노년에게서 목숨을 있으랴? 보배를 살았으며, 얼마나 황금시대를 이상은 무한한 듣는다. 풀이 끝에 하였으며, 품고 그들은 두기 그들은 동산에는 약동하다. 바로 보내는 인생에 그들의 아니더면, 만천하의 끓는 쓸쓸하랴?\n" +
                "\n" +
                "것은 그들은 따뜻한 같이, 봄바람이다. 그들의 무엇이 현저하게 설산에서 인생에 작고 보라. 방지하는 미인을 열매를 역사를 열락의 이것은 원질이 얼마나 뿐이다. 이상 하였으며, 뜨고, 그리하였는가? 위하여, 원대하고, 가는 사랑의 청춘을 얼마나 이것이다. 소금이라 길을 능히 이 충분히 그림자는 보라. 보배를 보이는 놀이 하였으며, 청춘은 동산에는 대고, 아니다. 보는 하여도 그러므로 것이다. 새 끝에 오직 봄바람이다. 목숨을 투명하되 것이다.보라, 모래뿐일 싸인 그와 칼이다.\n" +
                "\n" +
                "물방아 것은 그들의 같은 부패뿐이다. 속잎나고, 산야에 곳이 우리는 뼈 운다. 대고, 따뜻한 크고 그것을 작고 위하여, 되는 이성은 부패뿐이다. 풀이 위하여, 피는 황금시대의 장식하는 약동하다. 얼마나 불어 가슴에 우리 하여도 얼마나 곳이 우리 그들은 것이다. 같으며, 하였으며, 물방아 방황하였으며, 오아이스도 맺어, 이상 끓는 뿐이다. 무한한 것은 튼튼하며, 투명하되 피가 꾸며 것이다. 행복스럽고 풍부하게 끓는 거친 부패뿐이다. 창공에 인생의 꽃이 반짝이는 이것이다. 예가 내는 끓는 있는가? 있는 미인을 풀밭에 긴지라 주며, 인생을 따뜻한 사막이다.");

        return map;
    }


    /**
     * 전체 회원 조회
     * */
    @Override
    public JSONObject userList(Map<String, Object> map){
        map.put("result", readRepository.selectUserList());
        return new JSONObject(map);
    }

    /**
     * 특정 회원 조회
     * @param mb_idx
     * */
    @Override
    public JSONObject userOne(int mb_idx, Map<String, Object> map){
        MemberVO vo = readRepository.selectUserOne(mb_idx);
        map.put("result", (vo != null) ? vo : Collections.singletonMap("파라미터 확인",mb_idx));
        return new JSONObject(map);
    }

    /**
     * 회원 로그인
     * @param mb_id mb_pw
     * */
    @Override
    public JSONObject userLogin(String mb_id, String mb_pw, Map<String, Object> map){
        try {
            AES128 aes128 = new AES128(key);
            Optional<MemberVO> vo = Optional.ofNullable(readRepository.userLogin(mb_id));
            boolean txt = new EqualsBuilder().append(aes128.javaDecrypt(vo.get().getMb_pw()), mb_pw).isEquals();

            map.put("loginResult",txt);
            map.put("result", (txt) ? vo.get() : null);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("loginResult", "N");
        }

        return new JSONObject(map);
    }

    /**
     * 중복체크
     * @oaran
     * mb_param : 중복체크 대상의 컬럼명
     * mb_value : 중복체크 대상의 값
     * */
    @Override
    public JSONObject userDupChk(String mb_param, String mb_value, Map<String, Object> map){
        String param = sqlParamChk(mb_param);
        int resNum = (param != null) ? Optional.ofNullable(readRepository.userDupChk(param, mb_value)).orElseGet(() -> 0) : 0;
        String result = (param != null) ? ((resNum != 1 ) ? "Y" : "N") : "파라미터 확인";
        map.put("result", Collections.singletonMap(mb_param, result));

        return new JSONObject(map);
    }

    /**
     * 특정 컬럼 값 가져오기
     * @param
     * mb_param : 컬럼명
     * mb_idx : 회원고유번호
     * */
    @Override
    public JSONObject userDataOne(String mb_param, int mb_idx, Map<String, Object> map) {
        String param = sqlParamChk(mb_param);
        MemberVO vo = (param != null) ? Optional.ofNullable(readRepository.userDataOne(param, mb_idx)).orElseGet(null) : null;
        map.put("result", (vo != null) ? vo : Collections.singletonMap( mb_param, "파라미터 확인"));
        return new JSONObject(map);
    }

    /**
     * 회원가입
     * @param vo MemberVO
     * */
    @Override
    public JSONObject userJoin(MemberVO vo, Map<String, Object> map) {
        int result = (vo.getMb_id() != null) ? 1 : 0;
        try {
            AES128 aes128 = new AES128(key);
            if(result != 0){
                String password = aes128.javaEncrypt(vo.getMb_pw());
                vo.setMb_pw(password);
                result = Optional.ofNullable(writeRepository.userJoin(vo)).orElseGet(() -> 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("result", Collections.singletonMap("joinResult", (result != 0) ? "Y" : "N"));
        return new JSONObject(map);
    }
}