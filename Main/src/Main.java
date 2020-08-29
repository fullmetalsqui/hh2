import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final List<Vacancy> vacancies = new ArrayList<>();
    private static int countIntervals;
    private static int duration;

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            int countVacancies = Integer.parseInt(reader.readLine());
            if (countVacancies > 0) {
                for (int i = 0; i < countVacancies; i++) {
                    String[] tempVacancy = reader.readLine().split(" ");
                    vacancies.add(new Vacancy(Long.parseLong(tempVacancy[0]), Long.parseLong(tempVacancy[1])));
                }
            } else {
                System.out.println(countVacancies + " " + duration);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long globalStart = whenStart(vacancies);
        long globalEnd = whenEnd(vacancies);
        List<Integer> pastIndices = new ArrayList<>();
        int maxCountVacancies = 1;
        for (long sec = globalStart; sec <= globalEnd; sec++){
            List<Integer> currentIndices = new ArrayList<>();
            int countVacancies = 0;
            for (int j = 0; j < vacancies.size(); j++){
                if (isAlive(vacancies.get(j), sec)){
                    countVacancies++;
                    currentIndices.add(j);
                }
            }
            if (countVacancies > maxCountVacancies){
                maxCountVacancies = countVacancies;
                duration = 1;
                countIntervals = 1;
                if (pastIndices.isEmpty()){
                    pastIndices.addAll(currentIndices);
                } else {
                    pastIndices = new ArrayList<>(currentIndices);
                }
            }
            if (countVacancies == maxCountVacancies){
                duration++;
                if (!pastIndices.containsAll(currentIndices)){
                    countIntervals++;
                    pastIndices = new ArrayList<>(currentIndices);
                }
            }
        }
        System.out.println(countIntervals + " " + duration);
    }
    public static long whenStart(List<Vacancy> vacancyList){
        long start = vacancyList.get(0).start;
        for (Vacancy vacancy : vacancyList){
            if (vacancy.getStart() < start){
                start = vacancy.getStart();
            }
        }
        return start;
    }
    public static long whenEnd(List<Vacancy> vacancyList){
        long end = vacancyList.get(0).end;
        for (Vacancy vacancy : vacancyList){
            if (vacancy.getEnd() > end){
                end = vacancy.getEnd();
            }
        }
        return end;
    }
    public static boolean isAlive(Vacancy vacancy, long sec){
        for (int m = 0; m < vacancy.vacancy.size(); m++){
            if (vacancy.vacancy.get(m)== sec){
                return true;
            }
        }
        return false;
    }
    static class Vacancy{
        private final long start;
        private final long end;
        private final List<Long> vacancy = new ArrayList<>();

        public Vacancy(long start, long end) {
            this.start = start;
            this.end = end;
            for (long i = start; i <= end; i++){
                vacancy.add(i);
            }
        }
        public long getStart() {
            return start;
        }
        public long getEnd() {
            return end;
        }
    }
}
