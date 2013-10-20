package pi.shared;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import pi.shared.schemes.Scheme;
import pi.shared.schemes.signal.SignalScheme;
import pi.utilities.Margin;

//-------------------------------------------
/*
	SINGLETONIK - KLASA SHARED, WSPOLDZIELONA 
	PRZEZ WSZYSTKIE
	
	OGOLNIE WRZUCILEM TU JAKIES PIERDY POKI CO
	DO RYSOWANIA ECG
	
	NP MIN-SCALE , MAX-SCALE TO SA PRZEDZIALY 
	DOPUSZCZALNYCH SKAL WIDOKU
	
	SMIALO TU MOZNA SWOJE RZECZY WRZUCAC, KTORE 
	BEDA MUSIALY BYC WIDOCZNE W WIELU MIEJSCACH
	W PROGRAMIE
	
	TRZEBA PAMIETAC O INICIE TYCH ZMIENNYCH W
	PRYWATNYM KONSTRUKTORZE
	
	NO A INSTANCJE KLASY POBIERAMY PRZEZ 
	= SharedController.getInstance();
	
	TUTAJ TWORZY SIE DOMYSLNY SCHAMAT KOLORYSTYCZNY
	- TYLKO JEDEN BEDZIEMY WYKORZYSTYWAC JEDNAK
	WIEC NIE TRZEBA TWORZYC NOWYCH
	
	NO ALE W RAZIE WSZYSTKICH WATPLIWOSCI NATURY STYLISTYCZNEJ
	NP. KOLOR CZEGO�-TAM, TO WRZUCAMY ODPOWIEDNIE POLE DO 
	KLASY SCHEME, A TUTAJ W METODZIE CREATE WHITE SCHEME
	NADAJEMY KOLOREK, A W ODPOWIEDNIM MIEJSCU PROGRAMU
	POBIERAMY GO
*/
//-------------------------------------------

public class SharedController
{
	private static SharedController instance = null;
	
	private Scheme whiteScheme;
	private Scheme blackScheme;
	private Scheme currentScheme;
	
	private int maxSegments;
	private double pixelsForScale;
	
	private double minScale;
	private double maxScale;
	
	private double minInterval;
	private double maxInterval;
	
	private SharedController()
	{
		this.pixelsForScale = 100;
		this.maxSegments = 3;
		this.createWhiteScheme();
		this.createBlackScheme();
		this.setCurrentScheme(this.whiteScheme);
		this.minScale = 0.05d;
		this.maxScale = 0.5d;
		this.minInterval = 0.001d;
		this.maxInterval = 0.02d;
	}
	
	public static  SharedController getInstance()
	{
		if (instance == null) instance = new SharedController();
		return instance;
	}
	
	public void createWhiteScheme()
	{
		whiteScheme = new Scheme();
		
		SignalScheme signal = whiteScheme.getSignalScheme();
		signal.setBackgroundColor(new Color(255, 255, 255));
		signal.setBorderColor(new Color(0, 0, 0));
		signal.setMainGridColor(new Color(200, 200, 200));
		signal.setSubGridColor(new Color(230, 230, 230));
		signal.setGridColor(new Color(255, 255, 255));
		signal.setMargin(new Margin(60, 25, 35, 35));
		
		signal.setFontSize(12);
		signal.setFont(new Font("Serif", Font.PLAIN, signal.getFontSize()));
		signal.setFontColor(new Color(0, 0, 0));
		
		signal.setSignalColor(new Color(255, 0, 0));
		signal.setProbeColor(new Color(0, 0, 0));
		
		signal.setSelectColor(new Color(100, 100, 255, 123));
	
		signal.setP_WaveColor(new Color(145, 200, 0, 123));
		signal.setPr_SegmentColor(new Color(0, 40, 200, 123));
		signal.setQrs_SegmentColor(new Color(160, 200, 60, 123));
		signal.setSt_SegmentColor(new Color(145, 40, 60, 123));
		signal.setT_WaveColor(new Color(0, 200, 100, 123));
		signal.setU_WaveColor(new Color(255, 0, 0, 123));
		signal.setP_WaveColor(new Color(0, 255, 0, 123));
		signal.setMarkeredCycleColor(new Color(125, 255, 125, 123));
		signal.setCycleColor(new Color(255, 125, 125, 123));

		signal.setMainDivider(4);
		signal.setSubDivider(3);
		
		signal.setSignalStroke(new BasicStroke(1));
		signal.setProbeStroke(new BasicStroke(1));
	}
	
	public void createBlackScheme()
	{
		blackScheme = new Scheme();
		
		SignalScheme signal = blackScheme.getSignalScheme();
		signal.setBackgroundColor(new Color(0, 0, 0));
		signal.setBorderColor(new Color(255, 255, 255));
		signal.setMainGridColor(new Color(60, 60, 60));
		signal.setSubGridColor(new Color(30, 30, 30));
		signal.setGridColor(new Color(0, 0, 0));
		signal.setMargin(new Margin(60, 25, 15, 30));
		
		signal.setFontSize(12);
		signal.setFont(new Font("Serif", Font.PLAIN, signal.getFontSize()));
		signal.setFontColor(new Color(255, 255, 255));
		
		signal.setSignalColor(new Color(0, 255, 0));
		signal.setProbeColor(new Color(255, 255, 0));
		
		signal.setSelectColor(new Color(80, 80, 255, 123));

		signal.setMainDivider(4);
		signal.setSubDivider(3);
		
		signal.setSignalStroke(new BasicStroke(1));
		signal.setProbeStroke(new BasicStroke(1));
	}
	
	
	public Scheme getWhiteScheme()
	{
		return whiteScheme;
	}
	
	public Scheme getBlackScheme()
	{
		return blackScheme;
	}


	public int getMaxSegments()
	{
		return maxSegments;
	}

	public void setMaxSegments(int maxSegments)
	{
		this.maxSegments = maxSegments;
	}

	public Scheme getCurrentScheme()
	{
		return currentScheme;
	}

	public void setCurrentScheme(Scheme currentScheme)
	{
		this.currentScheme = currentScheme;
	}

	public double getPixelsForScale()
	{
		return pixelsForScale;
	}

	public void setPixelsForScale(double pixelsForScale)
	{
		this.pixelsForScale = pixelsForScale;
	}

	public double getMinScale()
	{
		return minScale;
	}

	public void setMinScale(double minScale)
	{
		this.minScale = minScale;
	}

	public double getMaxScale()
	{
		return maxScale;
	}

	public void setMaxScale(double maxScale)
	{
		this.maxScale = maxScale;
	}

	public double getMinInterval()
	{
		return minInterval;
	}

	public void setMinInterval(double minInterval)
	{
		this.minInterval = minInterval;
	}

	public double getMaxInterval()
	{
		return maxInterval;
	}

	public void setMaxInterval(double maxInterval)
	{
		this.maxInterval = maxInterval;
	}
	
}
