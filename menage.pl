
use File::Path 'rmtree';

rmtree('xmind7\XMind_Windows\configuration\org.eclipse.osgi') or warn ("Suppression imp");
rmtree('xmind7\data') or warn( "Suppression imp");