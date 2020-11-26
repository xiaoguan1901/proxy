package com.modify;

public class AddFWGZModifiy extends Modifiy {

	@Override
	public String changehtml(String htmlText) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(htmlText.replace("onclick=\"SelectOnlyAccounttxbText();\"","onclick=\"SelectOnlyAccounttxbTextProxy();\""));
		sBuffer.append("<script>");
		String m = "function SelectOnlyAccounttxbTextProxy() {"
				+"try {"
				+"	recdata = new Array();"
				+"	senddata = new Array();"
				+"	senddata[0] = document.getElementById('SelectOnlyAccount_txbText').value;"
				+"	senddata[1] = document.getElementById('SelectOnlyAccount_txbValue').value;"
				+"	senddata[2] = document.getElementById('SelectOnlyAccount_txbNodeID').value;"
				+"	window.location = \"/_layouts/IMPSamWebUI/select2/default.aspx?TargetSelectOnly=Y&TopOrganNodeID=430&OrganOutIn=010201&strOrganLevel="
				+"	&OrganFlag=010101&OrganType=&OrganShowOrderID=&OrganFiltrate=Y&OrganNodeID=&TargetType=Account&TargetAccountState=020302&TargetGroupType=030101"
				+"  &TargetOrganFlag=010101&TargetOrganType=010301&TargetOrganLevel=&TargetOneself=N&StrWCName=SelectOnlyAccounttxbText\";"
				+"	document.getElementById('SelectOnlyAccount_txbText').value = recdata[1];"
				+"	document.getElementById('SelectOnlyAccount_txbValue').value = recdata[0];"
				+"	document.getElementById('SelectOnlyAccount_txbNodeID').value = recdata[2];"
				+"} catch (err) {"
				+"	document.getElementById('SelectOnlyAccount_txbText').value = '';"
				+"	document.getElementById('SelectOnlyAccount_txbValue').value = '';"
				+"	document.getElementById('SelectOnlyAccount_txbNodeID').value = '';"
				+"}"
				+"event.cancelBubble = true;"
				+"event.returnValue = null;"
				+"return false;"
			+"}";
		sBuffer.append(m);
		sBuffer.append("</script>");
//		htmlText = htmlText + m;
		System.out.println(sBuffer.toString());
		return sBuffer.toString();
	}
	
	
	
//	Document doc = Jsoup.parse(text);
//	Elements link = doc.select("script");
//	ListIterator<Element> elem = link.listIterator();
//	while(elem.hasNext()) {
//		System.out.println(elem.next());
//	}
}
