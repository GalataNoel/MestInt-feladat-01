import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Reklam {
    private List<Rendeles> rendelesek;

    public Reklam() {
        this.rendelesek = new ArrayList<>();
    }

    public void beolvasRendelesek() {
        try {
            Scanner scanner = new Scanner(new File("rendel.txt"));
            while (scanner.hasNextLine()) {
                String sor = scanner.nextLine();
                String[] adatok = sor.split(" ");
                int nap = Integer.parseInt(adatok[0]);
                String varos = adatok[1];
                int darabszam = Integer.parseInt(adatok[2]);
                rendelesek.add(new Rendeles(nap, varos, darabszam));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void rendelesekSzama() {
        System.out.println("2. feladat");
        System.out.println("Rendelések száma: " + rendelesek.size());
    }

    public void rendelesekNapAlapjan() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("3. feladat");
        System.out.print("Kérem a nap számát: ");
        int napSzam = scanner.nextInt();

        int napRendelesek = 0;
        for (Rendeles rendeles : rendelesek) {
            if (rendeles.getNap() == napSzam) {
                napRendelesek++;
            }
        }

        System.out.println("A megadott napon " + napRendelesek + " rendelés történt.");
    }

    public void nemRendeltNapok() {
        System.out.println("4. feladat");
        Set<Integer> rendeltNapok = new HashSet<>();

        for (Rendeles rendeles : rendelesek) {
            rendeltNapok.add(rendeles.getNap());
        }

        int nemRendeltNapokSzama = 0;
        for (int nap = 1; nap <= 30; nap++) {
            if (!rendeltNapok.contains(nap)) {
                nemRendeltNapokSzama++;
            }
        }

        System.out.println("Nem rendelt napok száma: " + nemRendeltNapokSzama);
    }
    public void legnagyobbDarabszam() {
        System.out.println("5. feladat:");
        Optional<Rendeles> legnagyobbRendeles = rendelesek.stream()
                .max(Comparator.comparingInt(Rendeles::getDarabszam));

        if (legnagyobbRendeles.isPresent()) {
            Rendeles legnagyobb = legnagyobbRendeles.get();
            System.out.println("A legnagyobb darabszám: " + legnagyobb.getDarabszam() +
                    ", a rendelés napja: " + legnagyobb.getNap());
        } else {
            System.out.println("Nincs rendelés az adatokban");
        }
    }

    public void rendelesek21Napon() {
        System.out.println("7. feladat:");
        Map<String, Integer> rendelesek21Napon = new HashMap<>();

        for (Rendeles rendeles : rendelesek) {
            if (rendeles.getNap() == 21) {
                rendelesek21Napon.merge(rendeles.getVaros(), rendeles.getDarabszam(), Integer::sum);
            }
        }

        rendelesek21Napon.forEach((varos, darabszam) ->
                System.out.println("A rendelt termékek darabszáma a 21. napon " + varos + ": " + darabszam));
    }

    public void osszesitesVarnak() {
        System.out.println("8. feladat:");
        Map<String, int[]> osszesites = new HashMap<>();

        for (Rendeles rendeles : rendelesek) {
            int[] varosNapok = osszesites.computeIfAbsent(rendeles.getVaros(), k -> new int[3]);
            int napKategoria = rendeles.getNap() <= 10 ? 0 : (rendeles.getNap() <= 20 ? 1 : 2);
            varosNapok[napKategoria] += 1;
        }

        System.out.println("Napok\t1..10\t11..20\t21..30");
        osszesites.forEach((varos, napok) ->
                System.out.println(varos + "\t" + napok[0] + "\t" + napok[1] + "\t" + napok[2]));
    }

public static void main(String[] args) {
        Reklam reklam = new Reklam();
        reklam.beolvasRendelesek();
        reklam.rendelesekSzama();
        reklam.rendelesekNapAlapjan();
        reklam.nemRendeltNapok();
        reklam.legnagyobbDarabszam();
        reklam.rendelesek21Napon();
        reklam.osszesitesVarnak();
    }
}

class Rendeles {
    private int nap;
    private String varos;
    private int darabszam;

    public Rendeles(int nap, String varos, int darabszam) {
        this.nap = nap;
        this.varos = varos;
        this.darabszam = darabszam;
    }

    public int getNap() {
        return nap;
    }

    public String getVaros() {
        return varos;
    }

    public int getDarabszam() {
        return darabszam;
    }
}
