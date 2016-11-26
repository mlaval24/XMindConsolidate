
use File::Path 'rmtree';

rmtree('C:\mla\Informatique\xmind7\XMind_Windows\configuration\org.eclipse.osgi') or warn ("Suppression imp");

rmtree('C:\mla\Informatique\xmind7\data') or warn( "Suppression imp");

rmtree('C:\xmindp75\XMind_Windows\configuration\org.eclipse.osgi') or warn ("Suppression imp");
rmtree('C:\xmindp75\data') or warn( "Suppression imp");