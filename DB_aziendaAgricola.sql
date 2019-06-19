-- MySQL dump 10.13  Distrib 5.7.21, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: venditaweb
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE  IF NOT EXISTS `venditaweb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `venditaweb`;

--
-- Table structure for table `composizione`
--

DROP TABLE IF EXISTS `composizione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `composizione` (
  `Fattura` int(11) NOT NULL,
  `Prodotto` char(9) NOT NULL,
  `Sconto` decimal(2,0) DEFAULT NULL,
  `PrezzoAcquisto` decimal(6,2) DEFAULT NULL,
  `IVA` decimal(2,0) DEFAULT NULL,
  `Quantita` int(11) DEFAULT NULL,
  PRIMARY KEY (`Fattura`,`Prodotto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `composizione`
--

LOCK TABLES `composizione` WRITE;
/*!40000 ALTER TABLE `composizione` DISABLE KEYS */;
/*!40000 ALTER TABLE `composizione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fattura`
--

DROP TABLE IF EXISTS `fattura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fattura` (
  `NumeroFattura` int(11) NOT NULL AUTO_INCREMENT,
  `DataEmissione` date NOT NULL,
  `Importo` decimal(6,2) DEFAULT '0.00',
  `Cliente` char(16) DEFAULT NULL,
  `Partenza` varchar(50) NOT NULL,
  `Arrivo` varchar(50) NOT NULL,
  `DataPartenza` date DEFAULT NULL,
  `DataArrivo` date DEFAULT NULL,
  `SpeseSpedizione` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`NumeroFattura`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fattura`
--

LOCK TABLES `fattura` WRITE;
/*!40000 ALTER TABLE `fattura` DISABLE KEYS */;
/*!40000 ALTER TABLE `fattura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notizia`
--

DROP TABLE IF EXISTS `notizia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notizia` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Titolo` varchar(50) NOT NULL,
  `ContenutoTesto` varchar(2000) DEFAULT NULL,
  `DataInserimento` date DEFAULT NULL,
  `DataUltimaModifica` date DEFAULT NULL,
  `Tipo` varchar(20) DEFAULT NULL,
  `Immagine` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notizia`
--

LOCK TABLES `notizia` WRITE;
/*!40000 ALTER TABLE `notizia` DISABLE KEYS */;
INSERT INTO `notizia` VALUES (1,'Tracciabilità delle uova','Per tracciabilità si intende la possibilità di poter ricostruire il percorso del Prodotto, dall&apos;allevamento al bancone del punto vendita al dettaglio o il supermercato. <br>Un esempio, si considera questa cifra identificativa: 0 IT 022 SA 378 <br>La prima cifra indica la tipologia di allevamento: <b>0</b> IT 022 SA 378<br><table><tr><th> </th><th> </th></tr><tr><td>0 = Uova da agricoltura bilogica</td><td>indica che l&apos;alimentazione della gallina è a base di mangimi biologici di origine controllata, e senza additivi chimici</td></tr><tr><td>1 = Uova da allevamento all&apos;aperto</td><td>galline libere di razzolare tra la vegetazione</td></tr><tr><td>2 = Uova da allevamento a terra</td><td>vivono in capannoni illuminati con luce artificiale e pavimento di cemento, fino a nove galline a metro quadro</td></tr><tr><td>3 = Uova da allevamento in gabbie (batteria)</td><td>le galline sono allevate in gabbie di metallo con luce artificiale. Le uova deposte vengono convogliate ai macchinari di confezionamento mediante un nastro trasportatore che passa al di sotto delle gabbie.</td></tr></table><br>Segue un codice di due lettere che indica il paese di produzione: IT = Italia<br>Le prossime tre cifre indicano il codice ISTAT del comune di produzione: &quot;022&quot; è assegnato al comune di Campagna (SA) <br>Le prossime due lettere indicano la provincia di produzione: SA (Salerno) <br>Le ultime tre cifre identificano l&apos;allevamento di produzione: 378. Tale codice permette di risalire immediatamente, senza ambiguità ed in maniera univoca all&apos;azienda agricola a cui fa capo l&apos;allevamento. <br>Sul guscio è inoltre possibile trovare, a seconda della referenza acquistata, la data di deposizione o la data di scadenza dell&apos;uovo. ','2018-03-01','2018-04-05','generale','./images/news_tracciabilita.png'),(2,'Ovomont oltre il Biologico…','In risposta alla contaminazione delle uova al Fipronil che ha interessato e continua ad interessare molti allevatori d’Italia, abbiamo deciso volontariamente di aderire al “Protocollo d’Intesa rafforzato” siglato dalle associazioni di categoria: abbiamo pertanto sottoposto le nostre uova ad accurate analisi alla ricerca di Fipronil ed Amitraz. Abbiamo deciso di aderire a tale protocollo per dimostrare la totale estraneità delle nostre uova ai pesticidi oggetto di incriminazione. Aderire al protocollo significa ripetere le analisi con una frequenza di circa 100 giorni dall’ultimo test effettuato. <br> In difesa del “cibo buono” e in difesa dei consumatori più attenti abbiamo pertanto deciso di effettuare analisi anche sull’acqua che viene somministrata giornalmente ai nostri animali e sulla terra su cui le galline sono libere di razzolare, dimostrando in maniera molto profonda e concreta che la qualità dei prodotti che commercializziamo è reale.<br> ','2018-04-02','2018-05-10','generale','./images/news_123.jpg'),(3,'E’ finalmente arrivata l\'offerta del mese!!!','Questo mese mettiamo in offerta tre dei nostri prodotti con uno sconto pari fino al 20%!! In pratica su alcuni prodotti non facciamo pagare l\'iva.<table><tr><th> </th><th> </th></tr><tr><td>Confezione da 4 uova</td><td>Il suo prezzo originale + IVA è 2.56 €. Con lo sconto del 20%, il suo prezzo sarà 2.14 €</td></tr><tr><td>Confezione da 10 uova</td><td>Il suo prezzo originale + IVA è 4.76 €. Con lo sconto del 23%, il suo prezzo sarà 3.66€</td></tr><tr><td>Maionese</td><td>Il suo prezzo originale + IVA è 2 €. Con lo sconto del 20%, il suo prezzo sarà 1.54€</td></tr></table><br>Non farti scappare queste offerte, corri nella sezione \'Prodotti\' e comincia ad acquistare.<br>Le ricordiamo che per poter acquistare occorre registrarsi al sito. Per registrarsi cliccare su \'Accedi\' e selezionare \'Crea un nuovo account\'.','2018-07-01','2018-07-04','promozionale','./images/news_offerta_speciale.png');
/*!40000 ALTER TABLE `notizia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posta`
--

DROP TABLE IF EXISTS `posta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `posta` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Mittente` varchar(50) NOT NULL,
  `Destinatario` varchar(50) NOT NULL,
  `Oggetto` varchar(100) NOT NULL,
  `ContenutoTesto` varchar(500) DEFAULT NULL,
  `TipoPosta` varchar(20) DEFAULT NULL,
  `DataEmail` date DEFAULT NULL,
  `Ora` time DEFAULT NULL,
  `Lettura` tinyint(1) DEFAULT NULL,
  `TipoUtente` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posta`
--

LOCK TABLES `posta` WRITE;
/*!40000 ALTER TABLE `posta` DISABLE KEYS */;
/*!40000 ALTER TABLE `posta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prodotto`
--

DROP TABLE IF EXISTS `prodotto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prodotto` (
  `Codice` char(9) NOT NULL,
  `Descrizione` varchar(1000) DEFAULT NULL,
  `DataScadenza` date DEFAULT NULL,
  `DataConfezionamento` date DEFAULT NULL,
  `Prezzo` decimal(4,2) DEFAULT NULL,
  `Sconto` decimal(3,0) DEFAULT '0',
  `IVA` decimal(3,0) DEFAULT NULL,
  `Tipo` varchar(20) NOT NULL,
  `UovaConfezione` decimal(3,0) DEFAULT NULL,
  `Immagine` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Codice`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodotto`
--

LOCK TABLES `prodotto` WRITE;
/*!40000 ALTER TABLE `prodotto` DISABLE KEYS */;
INSERT INTO `prodotto` VALUES ('0-4005','<tr><td>Codice EAN Confezione: </td><td>8001858300627</td></tr>\n	<tr><td>Contenuto: </td><td>4 uova di gallina di vario calibro</td></tr>\n	<tr><td>Categoria di qualità: </td><td>Uova Fresche di Categoria A</td></tr>\n	<tr><td>Tipo allevamento: </td><td>A terra</td></tr>\n	<tr><td>Peso: </td><td>220gr</td></tr>\n	<tr><td>Tipo Confezione: </td><td>confezione in PET</td></tr>\n	<tr><td>Modalità di conservazione: </td><td>conservare in frigorifero dopo l’acquisto</td></tr>\n	<tr><td>Durata del prodotto: </td><td>28 giorni</td></tr>\n	<tr><td>Suggerimento: </td><td>si consiglia di consumare le uova prima di 10 giorni</td></tr>','2018-04-24','2018-04-24',2.16,20,22,'Confezione',4,'./images/uova4.jpg'),('0-6061','<tr><td>Codice EAN Confezione: </td><td>8008623107818</td></tr>\n	<tr><td>Contenuto: </td><td>6 uova di gallina di vario calibro</td></tr>\n	<tr><td>Categoria di qualità: </td><td>Uova Fresche di Categoria A</td></tr>\n	<tr><td>Tipo allevamento: </td><td>A terra</td></tr>\n	<tr><td>Peso: </td><td>330gr</td></tr>\n	<tr><td>Tipo Confezione: </td><td>confezione in PET</td></tr>\n	<tr><td>Modalità di conservazione: </td><td>conservare in frigorifero dopo l’acquisto</td></tr>\n	<tr><td>Durata del prodotto: </td><td>28 giorni</td></tr>\n	<tr><td>Suggerimento: </td><td>si consiglia di consumare le uova prima di 10 giorni</td></tr>','2018-04-24','2018-04-24',2.79,0,22,'Confezione',6,'./images/uova6.jpg'),('1-1078','<tr><td>Codice EAN Confezione: </td><td>8008620158920</td></tr>\n	<tr><td>Contenuto: </td><td>10 uova di gallina di vario calibro</td></tr>\n	<tr><td>Categoria di qualità: </td><td>Uova Fresche di Categoria A</td></tr>\n	<tr><td>Tipo allevamento: </td><td>A terra</td></tr>\n	<tr><td>Peso: </td><td>580gr</td></tr>\n	<tr><td>Tipo Confezione: </td><td>confezione in PET</td></tr>\n	<tr><td>Modalità di conservazione: </td><td>conservare in frigorifero dopo l’acquisto</td></tr>\n	<tr><td>Durata del prodotto: </td><td>28 giorni</td></tr>\n	<tr><td>Suggerimento: </td><td>si consiglia di consumare le uova prima di 10 giorni</td></tr>','2018-04-24','2018-04-24',3.79,23,22,'Confezione',10,'./images/uova10.png'),('1-1500','<tr><td>Codice EAN Confezione: </td><td>8008620150156</td></tr>\n	<tr><td>Contenuto: </td><td>15 uova di gallina di vario calibro</td></tr>\n	<tr><td>Categoria di qualità: </td><td>Uova Fresche di Categoria A</td></tr>\n	<tr><td>Tipo allevamento: </td><td>A terra</td></tr>\n	<tr><td>Peso: </td><td>860gr</td></tr>\n	<tr><td>Tipo Confezione: </td><td>confezione in PET</td></tr>\n	<tr><td>Modalità di conservazione: </td><td>conservare in frigorifero dopo l’acquisto</td></tr>\n	<tr><td>Durata del prodotto: </td><td>28 giorni</td></tr>\n	<tr><td>Suggerimento: </td><td>si consiglia di consumare le uova prima di 10 giorni</td></tr>','2018-04-24','2018-04-24',5.39,0,21,'Confezione',15,'./images/uova15.jpg'),('3-0198','I tuorli d\'uovo in busta provengono esclusivamente da galline allevate a terra, non vengono aggiunti coloranti nè conservanti in modo tale da conservarne il gusto tipico. Una confezione da 500g contiene all\'incirca 30 uova sgusciate.</p></td></tr><tr><td class=\"td_head\"><center><b>Info sulla conservazione</b></center></td></tr><tr><td><p>Da conservare in un luogo fresco e una volta aperta la confezione conservare in frigo. <br>Consumare il prodotto preferibilmente entro 37 giorni.','2018-04-24','2018-04-24',4.25,0,21,'Tuorlo d\'uovo',0,'./images/tuorlo.jpg'),('5-0034','La pasta all\'uovo è preparata solo con ingredienti biologici, seguendo la tradizionale ricetta e lavorazione che le conferiscono un sapore e una consistenza uniche. Le confezioni disponibili sono da 500g.','2018-04-24','2018-04-24',3.85,0,23,'Pasta all\'uovo',0,'./images/pasta.jpg'),('8-2489','La nostra maionese segue la ricetta originale ed è prodotta solo con ingredienti di prima qualità, tra cui le uova di nostra produzione; il processo di lavorazione è seguito attentamente e il prodotto finale è controllato in modo tale da garantire la massima qualità e bontà.</p></td></tr><tr><td class=\"td_head\"><center><b>Info sulla conservazione</b></center></td></tr><tr><td><p>La confezione disponibile in vendita è di 300g. <br>Da conservare in un luogo fresco e una volta aperta la confezione conservare in frigo. <br>Consumare il prodotto preferibilmente entro un mese.','2018-04-24','2018-04-24',1.75,20,18,'Maionese',0,'./images/maionese.jpg'),('9-3851','I Tarallucci sono preparati con uova fresche provenienti solo da galline allevate a terra. Frollini di semplice pastafrolla fragrante e leggera, per iniziare al meglio la giornata con il gusto unico delle cose autentiche e senza tempo.</p></td></tr><tr><td class=\"td_head\"><center><b>Info sulla conservazione</b></center></td></tr><tr><td><p>I biscotti sono preparati senza l\'utilizzo di conservanti. Si raccomanda, quindi, di tenerli in un luogo fresco e asciutto. <br>Da consumarsi preferibilmente entro la data indicata sulla confezione.','2018-04-29','2018-04-29',3.99,0,22,'Tarallucci con uova',0,'./images/pack_biscotto_tarallucci.png');
/*!40000 ALTER TABLE `prodotto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utente` (
  `CF` char(16) NOT NULL,
  `Nome` varchar(20) NOT NULL,
  `Cognome` varchar(20) NOT NULL,
  `DataNascita` date DEFAULT NULL,
  `LuogoNascita` varchar(20) DEFAULT NULL,
  `Sesso` char(1) NOT NULL,
  `Indirizzo` varchar(50) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  `Telefono` char(11) DEFAULT NULL,
  `Username` varchar(20) NOT NULL,
  `Pwd` varchar(20) NOT NULL,
  `Tipo` varchar(20) NOT NULL,
  PRIMARY KEY (`CF`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES ('LCLGRD96M13A509K','Gerardo','Laucella','1996-08-13','Avellino (AV)','M','Fontana Croce, 2 Nusco(AV)','gerardo96lm@hotmail.it','3897884341','gerryLM','GLM1234','admin'),('LCNRCC98B24A509U','Rocco','Lo Conte','1998-02-24','Avellino (AV)','M','Enrico Capozzi, 84 Avellino (AV)','lc24rocco@gmail.com','3662359991','rLoconte','RLC1234','admin'),('SMMFLV97P03H702L','Fulvio','Somma','1997-09-03','Salerno (SA)','M','Montuonica, 24 Minori (SA)','fulvio309@gmail.com','3316261302','FulvioS','FS12345','admin');
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-15 16:52:59
