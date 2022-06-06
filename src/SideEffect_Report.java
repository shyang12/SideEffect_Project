import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.awt.event.MouseAdapter;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Iterator;

import javax.swing.JScrollPane;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class SideEffect_Report {
	static String PATH = "./Side_Effect/";
	static String DIR = "./Side_Effect/tmp/";
	static String URL = "https://www.health.kr/researchInfo/adverse.asp?search_value=&HV_Scroll=1&search_term=all&paging_value=1&setLine=100&firstSearch=true";
	
	public static void main(String[] args) {
		URL ImageURL;
		Image image = null;
		BufferedImage fimage = null;
		
		Save_SETitle();
		Save_SELink();
		Load_SETitle();
		Load_SELink();
		Build_SE();
		//Build_SEAction();
		}
	
	public static void Print_CheckMsg(Boolean b)
	{
		if(b)
			System.out.println("\t[Checked!]");
		else
			System.out.println("\t[Error Occured!]");
	}
	
	 static boolean Save_SETitle()
	    {
	    	try {
	    		String PATH_TXT = "./Side_Effect/Side_Effect.txt";
	    		Document doc = Jsoup.connect(URL).get();
	    		
	    		Elements element =new Elements(Jsoup.connect(URL).get().select("td.txtL"));
	    		
	    		ListIterator<Element> Titles = element.listIterator();
	    		Init_txt(PATH_TXT);
	    		while(Titles.hasNext()) {
	    			String tmp = "";
	    			tmp = Titles.next().text();
	    			tmp = tmp.replace("NEW", "");
	    			String T_PATH = PATH + tmp + ".txt";
	    			Save_txt(tmp + "\n", PATH_TXT);
	    			
	    		}
	    		return true;
	    	} catch(Exception e) {e.printStackTrace(); return false;}
	    }
	 
	 static boolean Save_SELink() {
		 try {
			 String PATH_LINK = "./Side_Effect/Side_Effect_LINK.txt";
			 
			 Document doc = Jsoup.connect(URL).get();
			 Elements element =new Elements(Jsoup.connect(URL).get().select("td.txtL"));
			 ListIterator<Element> Links = element.listIterator();
			 Init_txt(PATH_LINK);
			 while(Links.hasNext()) {
				 Element tmpL = Links.next();
				 String tmp = tmpL.select("a").attr("onclick");
				 int first = tmp.indexOf("http");
				 int last = tmp.indexOf("',");
				 tmp = tmp.substring(first, last);
				 
				 Save_txt(tmp + "\n", PATH_LINK);
				 
			 }
			 
			 return true;
		 } catch(Exception e) {e.printStackTrace(); return false;}
	 }
	
	 static boolean Load_SETitle() {
		 try {
	    		String PATH_TXT = "./Side_Effect/Side_Effect.txt";
	    		Document doc = Jsoup.connect(URL).get();
	    		
	    		Elements element =new Elements(Jsoup.connect(URL).get().select("td.txtL"));
	    		
	    		ListIterator<Element> Titles = element.listIterator();
	    		while(Titles.hasNext()) {
	    			String tmp = "";
	    			tmp = Titles.next().text();
	    			tmp = tmp.replace("NEW", "");
	    			String T_PATH = PATH + tmp + ".txt";
	    		}
	    		ListIterator<String> L1 = Load_txt(PATH_TXT);
	    		return true;
	    	} catch(Exception e) {e.printStackTrace(); return false;}
	}
	
	
	 static boolean Load_SELink()
	    {
		 try {
			 String PATH_LINK = "./Side_Effect/Side_Effect_LINK.txt";
			 
			 Document doc = Jsoup.connect(URL).get();
			 Elements element =new Elements(Jsoup.connect(URL).get().select("td.txtL"));
			 ListIterator<Element> Links = element.listIterator();
			 while(Links.hasNext()) {
				 Element tmpL = Links.next();
				 String tmp = tmpL.select("a").attr("onclick");
				 int first = tmp.indexOf("http");
				 int last = tmp.indexOf("',");
				 tmp = tmp.substring(first, last);
			 }
			 ListIterator<String> L2 = Load_txt(PATH_LINK);
			 return true;
		 } catch(Exception e) {e.printStackTrace(); return false;}
	}
	
	static boolean Build_SE() {
		 try {
	    		String PATH_TXT = "./Side_Effect/Side_Effect.txt";
	    		Document doc = Jsoup.connect(URL).get();
	    		
	    		Elements element =new Elements(Jsoup.connect(URL).get().select("td.txtL"));
	    		
	    		ListIterator<Element> Titles = element.listIterator();
	    		while(Titles.hasNext()) {
	    			String tmp = "";
	    			tmp = Titles.next().text();
	    			tmp = tmp.replace("NEW", "");
	    			String T_PATH = PATH + tmp + ".txt";
	    		}
	    		ListIterator<String> L1 = Load_txt(PATH_TXT);
	    		
	    		String PATH_LINK = "./Side_Effect/Side_Effect_LINK.txt";
				 
				 Document doc2 = Jsoup.connect(URL).get();
				 Elements element2 =new Elements(Jsoup.connect(URL).get().select("td.txtL"));
				 ListIterator<Element> Links = element.listIterator();
				 int AMOUNT = 100;
				 int n= 0;
				 String[] URL_LINK = new String[AMOUNT];
				 
				 while(Links.hasNext()) {
					 Element tmpL = Links.next();
					 String tmp = tmpL.select("a").attr("onclick");
					 int first = tmp.indexOf("http");
					 int last = tmp.indexOf("',");
					 tmp = tmp.substring(first, last);
					 URL_LINK[n++] = tmp;
				 }
				 ListIterator<String> L2 = Load_txt(PATH_LINK);
		
	    		JFrame Frame = new JFrame("부작용 리포트");
	    		JScrollPane JS = new JScrollPane();
	    		JList list;
	    		
	    		Frame.setLayout(null);
	    		Frame.setSize(600, 900);
	    		Frame.setVisible(true);
	    		
	    		if(L1 == null) {
	    			System.out.println("파일이 없습니다.");
	    			return false;
	    		}
	    		JS.setSize(585,880);
	    		Frame.add(JS);
	    		list = Build_List(L1);
	    		if(list == null) {
	    			return false;
	    		}
	    		list.addMouseListener(new MouseAdapter() {											// JList에 더블클릭 이벤트 추가
	    			public void mouseClicked(MouseEvent e) {
	    				if(e.getClickCount() == 2) {
	    					// 더블 클릭 시
	    					int item = list.locationToIndex(e.getPoint());										// 클릭한 리스트의 원소 인덱스를 가져옴
	    					goWebsite(URL_LINK[item]);
	    				}
	    			}
	    		});

	    		JS.setViewportView(list);
	    		
	    		return true;
	} catch(Exception e) {e.printStackTrace();}
		return false;
	}
	
	private static void goWebsite(String url) 
    {
		try { Desktop.getDesktop().browse(new URI(url)); } 
		catch (URISyntaxException | IOException ex) {ex.printStackTrace();}
    }
	
	public static void Init_Iter(ListIterator L) 
	    {
	        while (L.hasPrevious())
	            L.previous();
	    }
	    
	    // String[] to ListIterator<String>
	    // ## Verified
	    public static ListIterator<String> Str2ListIter(String[] S) 
	    {
	    	
	    	LinkedList<String> list = new LinkedList<String>();
	    	ListIterator<String> L = list.listIterator();
			try 
			{	
				for(int i = 0; i < S.length; i++) 
					list.add(S[i]);
				L = list.listIterator();
			} catch (Exception e) {	System.out.println("Iterator로의 변환을 실패하였습니다"); }
			
			return L;
		}
		
	    // Elements to ListIterator<Element>
	    // ## Verified
		public static ListIterator<Element> Ele2ListIter(Elements E) 
		{
			try
			{
				return E.listIterator();
			}
			catch (Exception e) {System.out.println("Iterator로의 변환을 실패하였습니다"); return E.listIterator();}

		}
	    
	    // Check Iterator Generic
	    // ## Verified
	    public static String Check_Iter(ListIterator L)
	    {
	    	Init_Iter(L);
	        String tmp = L.next().getClass().getSimpleName();
	        Init_Iter(L);
	        System.out.println("This ListIterator Generic is " + tmp);
	        
	        return tmp;
	    }

	    // Initialize Text File
	    // ## Verified
	    public static Boolean Init_txt(String url) 
	    {
	        try
	        {
	        	File f = new File(url);
	        	if(f.delete())
	        	{
	        		System.out.println(f.getName() + " : Deleted!");
	        		Save_txt("", url);
	        		System.out.println(f.getName() + " : Regenerated!");
	        		return true;
	        	}
	        	else
	        	{
	        		Save_txt("", url);
	        		System.out.println(f.getName() + " : Generated!");
	        		return true;
	        	}
	        	
	        } catch(Exception e) {e.printStackTrace(); return false;}
	    }
	    
	 	// Load TextFile on ListIterator
	    // ## Verified
	    public static ListIterator<String> Load_txt(String File_URL) 
	    {
	    	ListIterator<String> L1;
	    	List<String> listTest = new LinkedList<String>();
	    	try 
	    	{
	             File f = new File(File_URL);
	             BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf-8"));

	             String s;

	             while((s = buf.readLine()) != null) 
	            	 listTest.add(s);
	                 
	             buf.close();
	    	 } catch (Exception e) {e.printStackTrace();}
	    	 
	    	 L1 = listTest.listIterator();
	             
	    	 while(L1.hasNext())
	    		 System.out.println(L1.next());
	             
	         Init_Iter(L1);

	         return L1;
	    }
	    
	    // Save text textfile
	    // ## Verified
	    public static Boolean Save_txt(String txt, String url)
	    {
	    	System.out.println("[Save Text...]");
	        try 
	        {
	        	File f = new File(url);
	        	
	        	if(!f.exists())
	        		f.createNewFile();
	        		
	        	BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, true), StandardCharsets.UTF_8));
	        	
	        	output.write(txt);
	        	output.close();
	        	
	            return true;

	        } catch (IOException ioe) {ioe.getStackTrace(); return false;}

	    }
	    
	 	// Check Directory
	    // ## Verified
	    public static Boolean Check_Dir(String path) 
	    {
	        File file = new File(path);
	        
	        if(!file.exists())
	        {
	            file.mkdirs();
	            System.out.println("Directory is created!");
	            return true;
	        }
	        else 
	        {
	        	System.out.println("Directory was created!");
	        	return false; 
	        }
	    }
	    
	    // Build JTree by Using ListIterator on JFrame
		// ## Verified
	    public static Boolean Build_Tree(JFrame JF, ListIterator L)
	    {
	        DefaultMutableTreeNode root = new DefaultMutableTreeNode("ROOT");        //루트폴더 1개 추가하려면 만들어야 됨.
	        DefaultMutableTreeNode low1 = new DefaultMutableTreeNode("LOW1");        //루트폴더에서 하위폴더 1개 추가하려면 만들어야 됨. 
	        root.add(low1);
	        
	        JTree JT = new JTree();
	        DefaultTreeModel model = new DefaultTreeModel(root);
	        JT.setModel(model);
	        
	        int width = JF.getSize().width - 35;
	        int height = JF.getSize().height - 60;
	        
	        if(Check_Iter(L).equals("String")) 
	        {
	            System.out.println("\nString 형으로 받음.");
	            ListIterator<String> tmpL = L;
	            try 
	            {
	                while(tmpL.hasNext()) 
	                {
	                    DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(tmpL.next());
	                    low1.add(node1);
	                }
	                JT.setBounds(10, 10, width, height);
	                JF.add(JT);
	                JF.repaint();
	                return true;
	            } catch (Exception e) {System.out.println("JTree 삽입 실패"); return false;}
	        }
	        
	        else if(Check_Iter(L).equals("Element")) 
	        {
	            System.out.println("\nString 형으로 받음.");
	            ListIterator<Element> tmpL = L;
	            try 
	            {
	                while(tmpL.hasNext()) 
	                {
	                    DefaultMutableTreeNode node2 = new DefaultMutableTreeNode(tmpL.next().text());
	                    low1.add(node2);
	                }
	                JT.setBounds(10, 10, width, height);
	                JF.add(JT);
	                JF.repaint();
	                return true;
	            } catch (Exception e) {System.out.println("JTree 삽입 실패"); return false;}
	        }
	        
	        else //Unknown Generic
	        	return false;
	        

	    }

	    // Build JList by Using ListIterator on JFrame
		// ## Verified
	    public static JList Build_List(ListIterator L) 
	    {
			
			JList jL = new JList(new DefaultListModel());
			DefaultListModel model = (DefaultListModel) jL.getModel();
			//jL.setBounds(10, 10, 270, 480);
			
			if(Check_Iter(L).equals("String")) 
			{
				//System.out.println("\nString 형으로 받음");
				ListIterator<String> tmpL = L;
				try 
				{
					while(tmpL.hasNext()) 
						model.addElement(tmpL.next());
					return jL;
				} catch (Exception e) {	System.out.println("JList 삽입 실패"); return null;}
			}
			
			else if(Check_Iter(L).equals("Element")) 
			{
				//System.out.println("\nElement 형으로 받음");
				ListIterator<Element> tmpL = L;
				try 
				{	
					while(tmpL.hasNext()) 
						model.addElement(tmpL.next().text());
					return jL;
				} catch (Exception e) {	System.out.println("JList 삽입 실패"); return null;}
			}
			else //Unknown Generic
				return null;
		}
	
}